package com.filerecovery.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "email_accounts")
data class EmailAccount(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String,
    val provider: EmailProvider,
    val accessToken: String,
    val refreshToken: String,
    val tokenExpiry: Long,
    val isLinked: Boolean = true,
    val syncStatus: SyncStatus = SyncStatus.IDLE,
    val lastSyncTime: Long = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class EmailProvider {
    GMAIL, OUTLOOK, IMAP
}

enum class SyncStatus {
    IDLE, SYNCING, COMPLETED, ERROR
}
