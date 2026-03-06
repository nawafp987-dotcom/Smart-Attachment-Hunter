package com.filerecovery.util

import com.filerecovery.data.model.FileType
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileHelper {

    fun formatFileSize(bytes: Long): String {
        return when {
            bytes <= 0 -> "0 B"
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> "${bytes / 1024} KB"
            bytes < 1024 * 1024 * 1024 -> "${String.format("%.2f", bytes / (1024f * 1024f))} MB"
            else -> "${String.format("%.2f", bytes / (1024f * 1024f * 1024f))} GB"
        }
    }

    fun formatDate(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(date)
    }

    fun formatDateTime(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun getFileExtension(fileName: String): String {
        return if (fileName.contains(".")) {
            fileName.substringAfterLast(".").lowercase()
        } else {
            ""
        }
    }

    fun determineFileType(fileName: String): FileType {
        val extension = getFileExtension(fileName).lowercase()

        return when {
            extension in listOf("jpg", "jpeg", "png", "gif", "bmp", "webp", "svg") -> FileType.IMAGE
            extension in listOf("mp4", "avi", "mkv", "mov", "flv", "wmv", "webm") -> FileType.VIDEO
            extension in listOf("mp3", "wav", "m4a", "flac", "aac", "ogg") -> FileType.AUDIO
            extension in listOf(
                "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
                "txt", "csv", "json", "xml", "html"
            ) -> FileType.DOCUMENT
            extension in listOf("zip", "rar", "7z", "tar", "gz", "bz2") -> FileType.ARCHIVE
            else -> FileType.OTHER
        }
    }

    fun getMimeType(fileName: String): String {
        return when (getFileExtension(fileName).lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "bmp" -> "image/bmp"
            "pdf" -> "application/pdf"
            "doc" -> "application/msword"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "xls" -> "application/vnd.ms-excel"
            "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            "txt" -> "text/plain"
            "mp3" -> "audio/mpeg"
            "mp4" -> "video/mp4"
            "zip" -> "application/zip"
            else -> "application/octet-stream"
        }
    }

    fun isValidFileName(fileName: String): Boolean {
        val invalidChars = listOf('/', '\\', ':', '*', '?', '"', '<', '>', '|', '\u0000')
        return fileName.isNotEmpty() && !invalidChars.any { fileName.contains(it) }
    }

    fun getSafeFileName(fileName: String): String {
        val invalidChars = listOf('/', '\\', ':', '*', '?', '"', '<', '>', '|', '\u0000')
        var safeName = fileName
        for (char in invalidChars) {
            safeName = safeName.replace(char, '_')
        }
        return safeName.trim('_')
    }

    fun getUniqueFileName(targetDir: File, fileName: String): String {
        var newFileName = fileName
        var counter = 1

        while (File(targetDir, newFileName).exists()) {
            val extension = getFileExtension(fileName)
            val nameWithoutExt = fileName.substringBeforeLast(".")

            newFileName = if (extension.isNotEmpty()) {
                "$nameWithoutExt($counter).$extension"
            } else {
                "$fileName($counter)"
            }
            counter++
        }

        return newFileName
    }

    fun deleteFile(file: File): Boolean {
        return try {
            if (file.isDirectory) {
                file.listFiles()?.forEach { deleteFile(it) }
            }
            file.delete()
        } catch (e: Exception) {
            false
        }
    }

    fun getFileSize(file: File): Long {
        return if (file.isDirectory) {
            file.listFiles()?.sumOf { getFileSize(it) } ?: 0
        } else {
            file.length()
        }
    }

    fun isImageFile(fileName: String): Boolean {
        val extension = getFileExtension(fileName).lowercase()
        return extension in listOf("jpg", "jpeg", "png", "gif", "bmp", "webp", "svg")
    }

    fun isVideoFile(fileName: String): Boolean {
        val extension = getFileExtension(fileName).lowercase()
        return extension in listOf("mp4", "avi", "mkv", "mov", "flv", "wmv", "webm")
    }

    fun isAudioFile(fileName: String): Boolean {
        val extension = getFileExtension(fileName).lowercase()
        return extension in listOf("mp3", "wav", "m4a", "flac", "aac", "ogg")
    }

    fun isDocumentFile(fileName: String): Boolean {
        val extension = getFileExtension(fileName).lowercase()
        return extension in listOf(
            "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
            "txt", "csv", "json", "xml", "html"
        )
    }
}
