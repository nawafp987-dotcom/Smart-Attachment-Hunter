package com.filerecovery.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.filerecovery.data.model.EmailAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface EmailAccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmailAccount(emailAccount: EmailAccount): Long

    @Update
    suspend fun updateEmailAccount(emailAccount: EmailAccount)

    @Delete
    suspend fun deleteEmailAccount(emailAccount: EmailAccount)

    @Query("SELECT * FROM email_accounts WHERE id = :id")
    suspend fun getEmailAccountById(id: Long): EmailAccount?

    @Query("SELECT * FROM email_accounts")
    suspend fun getAllEmailAccounts(): List<EmailAccount>

    @Query("SELECT * FROM email_accounts")
    fun observeAllEmailAccounts(): Flow<List<EmailAccount>>

    @Query("SELECT * FROM email_accounts WHERE email = :email")
    suspend fun getEmailAccountByEmail(email: String): EmailAccount?

    @Query("SELECT * FROM email_accounts WHERE isLinked = 1")
    suspend fun getLinkedAccounts(): List<EmailAccount>

    @Query("SELECT * FROM email_accounts WHERE isLinked = 1")
    fun observeLinkedAccounts(): Flow<List<EmailAccount>>

    @Query("DELETE FROM email_accounts WHERE id = :id")
    suspend fun deleteAccountById(id: Long)

    @Query("UPDATE email_accounts SET syncStatus = :status, lastSyncTime = :timestamp WHERE id = :id")
    suspend fun updateSyncStatus(id: Long, status: String, timestamp: Long)

    @Query("UPDATE email_accounts SET accessToken = :token, tokenExpiry = :expiry WHERE id = :id")
    suspend fun updateToken(id: Long, token: String, expiry: Long)
}
