package com.filerecovery.data.api

import com.filerecovery.data.model.EmailAccount
import com.filerecovery.data.model.FileType
import com.filerecovery.data.model.RecoveredFile
import com.filerecovery.data.security.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.security.MessageDigest
import java.util.Base64

data class GmailAttachment(
    val id: String,
    val filename: String,
    val mimeType: String,
    val size: Long,
    val data: String? = null
)

data class GmailMessage(
    val id: String,
    val threadId: String,
    val labelIds: List<String>,
    val snippet: String,
    val historyId: String,
    val internalDate: Long,
    val payload: MessagePayload,
    val sizeEstimate: Long
)

data class MessagePayload(
    val mimeType: String,
    val headers: List<Header>,
    val parts: List<MessagePart>?,
    val body: MessageBody?
)

data class Header(
    val name: String,
    val value: String
)

data class MessagePart(
    val partId: String,
    val mimeType: String,
    val filename: String?,
    val headers: List<Header>,
    val body: MessageBody?
)

data class MessageBody(
    val size: Long,
    val data: String? = null,
    val attachmentId: String? = null
)

class EmailService(
    private val tokenManager: TokenManager
) {

    private val _syncProgress = MutableStateFlow<SyncProgress>(SyncProgress.Idle)
    val syncProgress: Flow<SyncProgress> = _syncProgress

    suspend fun scanEmailAttachments(emailAccount: EmailAccount): Result<List<RecoveredFile>> = runCatching {
        _syncProgress.emit(SyncProgress.Syncing(0))

        val decryptedToken = tokenManager.decryptToken(emailAccount.accessToken)
        val messages = mutableListOf<GmailMessage>()
        val recoveredFiles = mutableListOf<RecoveredFile>()

        _syncProgress.emit(SyncProgress.Syncing(50))

        for (message in messages) {
            val attachments = extractAttachments(message)
            for (attachment in attachments) {
                val fileType = determineFileType(attachment.mimeType)
                val fileHash = calculateFileHash(attachment.data ?: "")

                val recoveredFile = RecoveredFile(
                    emailAccountId = emailAccount.id,
                    fileName = attachment.filename,
                    fileType = fileType,
                    fileSize = attachment.size,
                    fileHash = fileHash,
                    sourceFolder = message.payload.headers.find { it.name == "From" }?.value ?: "Unknown",
                    downloadUrl = "gmail://attachment/${message.id}/${attachment.id}",
                    dateReceived = message.internalDate,
                    senderEmail = message.payload.headers.find { it.name == "From" }?.value ?: "",
                    messageId = message.id,
                    messageSubject = message.payload.headers.find { it.name == "Subject" }?.value ?: ""
                )

                recoveredFiles.add(recoveredFile)
            }
        }

        _syncProgress.emit(SyncProgress.Completed)
        recoveredFiles
    }

    private fun extractAttachments(message: GmailMessage): List<GmailAttachment> {
        val attachments = mutableListOf<GmailAttachment>()

        message.payload.parts?.forEach { part ->
            if (part.filename != null && part.filename.isNotEmpty()) {
                attachments.add(
                    GmailAttachment(
                        id = part.body?.attachmentId ?: "",
                        filename = part.filename,
                        mimeType = part.mimeType,
                        size = part.body?.size ?: 0
                    )
                )
            }
        }

        return attachments
    }

    private fun determineFileType(mimeType: String): FileType {
        return when {
            mimeType.startsWith("image/") -> FileType.IMAGE
            mimeType.startsWith("video/") -> FileType.VIDEO
            mimeType.startsWith("audio/") -> FileType.AUDIO
            mimeType in listOf(
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.ms-excel",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            ) -> FileType.DOCUMENT
            mimeType in listOf(
                "application/zip",
                "application/x-rar-compressed",
                "application/x-7z-compressed",
                "application/x-tar"
            ) -> FileType.ARCHIVE
            else -> FileType.OTHER
        }
    }

    private fun calculateFileHash(data: String): String {
        val bytes = data.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }
}

sealed class SyncProgress {
    object Idle : SyncProgress()
    data class Syncing(val progress: Int) : SyncProgress()
    object Completed : SyncProgress()
    data class Error(val message: String) : SyncProgress()
}
