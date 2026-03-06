package com.filerecovery.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "recovered_files",
    foreignKeys = [
        ForeignKey(
            entity = EmailAccount::class,
            parentColumns = ["id"],
            childColumns = ["emailAccountId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecoveredFile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val emailAccountId: Long,
    val fileName: String,
    val fileType: FileType,
    val fileSize: Long,
    val fileHash: String,
    val sourceFolder: String,
    val downloadUrl: String,
    val thumbnailUrl: String? = null,
    val dateReceived: Long,
    val senderEmail: String,
    val messageId: String,
    val messageSubject: String,
    val isDownloaded: Boolean = false,
    val downloadPath: String? = null,
    val isStarred: Boolean = false,
    val isDuplicate: Boolean = false,
    val duplicateOf: Long? = null,
    val recovery_status: RecoveryStatus = RecoveryStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class FileType {
    IMAGE, VIDEO, DOCUMENT, AUDIO, ARCHIVE, OTHER
}

enum class RecoveryStatus {
    PENDING, DOWNLOADING, COMPLETED, FAILED, SKIPPED
}

data class FileFilter(
    val fileTypes: List<FileType> = FileType.values().toList(),
    val startDate: Long? = null,
    val endDate: Long? = null,
    val sourceApps: List<String> = emptyList(),
    val minSize: Long = 0,
    val maxSize: Long = Long.MAX_VALUE,
    val excludeDuplicates: Boolean = true,
    val senderEmail: String? = null
)
