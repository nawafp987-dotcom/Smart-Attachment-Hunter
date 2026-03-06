package com.filerecovery.data.api

import com.filerecovery.data.model.EmailAccount
import com.filerecovery.data.model.EmailProvider
import com.filerecovery.data.security.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Body

interface GmailAuthApi {
    @POST("https://oauth2.googleapis.com/token")
    suspend fun exchangeCodeForToken(@Body tokenRequest: TokenRequest): TokenResponse

    @GET("https://www.googleapis.com/oauth2/v2/userinfo")
    suspend fun getUserInfo(): GmailUserInfo
}

interface OutlookAuthApi {
    @POST("https://login.microsoftonline.com/common/oauth2/v2.0/token")
    suspend fun exchangeCodeForToken(@Body tokenRequest: TokenRequest): TokenResponse

    @GET("https://graph.microsoft.com/v1.0/me")
    suspend fun getUserInfo(): OutlookUserInfo
}

data class TokenRequest(
    val client_id: String,
    val client_secret: String,
    val code: String,
    val redirect_uri: String,
    val grant_type: String = "authorization_code"
)

data class TokenResponse(
    val access_token: String,
    val refresh_token: String?,
    val expires_in: Long,
    val token_type: String
)

data class GmailUserInfo(
    val id: String,
    val email: String,
    val name: String,
    val picture: String?
)

data class OutlookUserInfo(
    val id: String,
    val userPrincipalName: String,
    val displayName: String,
    val mail: String
)

class AuthService(
    private val tokenManager: TokenManager,
    private val gmailAuthApi: GmailAuthApi,
    private val outlookAuthApi: OutlookAuthApi
) {

    private val _currentUser = MutableStateFlow<String?>(null)
    val currentUser: StateFlow<String?> = _currentUser

    private val _emailAccounts = MutableStateFlow<List<EmailAccount>>(emptyList())
    val emailAccounts: StateFlow<List<EmailAccount>> = _emailAccounts

    suspend fun authenticateGmail(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ): Result<EmailAccount> = runCatching {
        val tokenRequest = TokenRequest(
            client_id = clientId,
            client_secret = clientSecret,
            code = code,
            redirect_uri = redirectUri
        )

        val tokenResponse = gmailAuthApi.exchangeCodeForToken(tokenRequest)
        val userInfo = gmailAuthApi.getUserInfo()

        val encryptedAccessToken = tokenManager.encryptToken(tokenResponse.access_token)
        val encryptedRefreshToken = tokenResponse.refresh_token?.let {
            tokenManager.encryptToken(it)
        } ?: ""

        val emailAccount = EmailAccount(
            email = userInfo.email,
            provider = EmailProvider.GMAIL,
            accessToken = encryptedAccessToken,
            refreshToken = encryptedRefreshToken,
            tokenExpiry = System.currentTimeMillis() + (tokenResponse.expires_in * 1000)
        )

        _emailAccounts.value = _emailAccounts.value + emailAccount
        emailAccount
    }

    suspend fun authenticateOutlook(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ): Result<EmailAccount> = runCatching {
        val tokenRequest = TokenRequest(
            client_id = clientId,
            client_secret = clientSecret,
            code = code,
            redirect_uri = redirectUri
        )

        val tokenResponse = outlookAuthApi.exchangeCodeForToken(tokenRequest)
        val userInfo = outlookAuthApi.getUserInfo()

        val encryptedAccessToken = tokenManager.encryptToken(tokenResponse.access_token)
        val encryptedRefreshToken = tokenResponse.refresh_token?.let {
            tokenManager.encryptToken(it)
        } ?: ""

        val emailAccount = EmailAccount(
            email = userInfo.mail,
            provider = EmailProvider.OUTLOOK,
            accessToken = encryptedAccessToken,
            refreshToken = encryptedRefreshToken,
            tokenExpiry = System.currentTimeMillis() + (tokenResponse.expires_in * 1000)
        )

        _emailAccounts.value = _emailAccounts.value + emailAccount
        emailAccount
    }

    suspend fun refreshTokenIfNeeded(emailAccount: EmailAccount): String {
        val currentTime = System.currentTimeMillis()
        if (currentTime > emailAccount.tokenExpiry - 5 * 60 * 1000) {
            return when (emailAccount.provider) {
                EmailProvider.GMAIL -> refreshGmailToken(emailAccount)
                EmailProvider.OUTLOOK -> refreshOutlookToken(emailAccount)
                EmailProvider.IMAP -> emailAccount.accessToken
            }
        }
        return emailAccount.accessToken
    }

    private suspend fun refreshGmailToken(emailAccount: EmailAccount): String {
        val decryptedRefreshToken = tokenManager.decryptToken(emailAccount.refreshToken)
        val accessToken = tokenManager.decryptToken(emailAccount.accessToken)
        return accessToken
    }

    private suspend fun refreshOutlookToken(emailAccount: EmailAccount): String {
        val decryptedRefreshToken = tokenManager.decryptToken(emailAccount.refreshToken)
        val accessToken = tokenManager.decryptToken(emailAccount.accessToken)
        return accessToken
    }

    fun logout() {
        _currentUser.value = null
        _emailAccounts.value = emptyList()
    }
}
