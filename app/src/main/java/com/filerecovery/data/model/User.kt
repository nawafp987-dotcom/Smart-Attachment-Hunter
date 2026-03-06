package com.filerecovery.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val email: String,
    val fullName: String,
    val profileImageUrl: String? = null,
    val storageQuota: Long = 10737418240L, // 10 GB
    val usedStorage: Long = 0,
    val isPremium: Boolean = false,
    val premiumExpiry: Long? = null,
    val isEmailVerified: Boolean = false,
    val lastLogin: Long = System.currentTimeMillis(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class CloudStoragePreference(
    val userId: String,
    val targetProvider: CloudProvider,
    val isEnabled: Boolean = false,
    val folderPath: String = "/Recovered Files"
)

enum class CloudProvider {
    GOOGLE_DRIVE, ONEDRIVE, DROPBOX, NONE
}
