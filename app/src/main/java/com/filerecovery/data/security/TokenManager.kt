package com.filerecovery.data.security

import android.content.Context
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class TokenManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPreferences = EncryptedSharedPreferences.create(
        context,
        "email_tokens",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val secretKey: SecretKey by lazy {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        keyGenerator.generateKey()
    }

    fun encryptToken(token: String): String {
        return try {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val encryptedBytes = cipher.doFinal(token.toByteArray())
            Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            token
        }
    }

    fun decryptToken(encryptedToken: String): String {
        return try {
            val encryptedBytes = Base64.decode(encryptedToken, Base64.DEFAULT)
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            String(cipher.doFinal(encryptedBytes))
        } catch (e: Exception) {
            encryptedToken
        }
    }

    fun saveToken(key: String, token: String) {
        encryptedPreferences.edit().putString(key, encryptToken(token)).apply()
    }

    fun getToken(key: String): String? {
        val encryptedToken = encryptedPreferences.getString(key, null)
        return encryptedToken?.let { decryptToken(it) }
    }

    fun removeToken(key: String) {
        encryptedPreferences.edit().remove(key).apply()
    }

    fun clearAllTokens() {
        encryptedPreferences.edit().clear().apply()
    }
}
