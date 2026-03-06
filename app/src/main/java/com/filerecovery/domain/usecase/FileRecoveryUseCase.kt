package com.filerecovery.domain.usecase

import com.filerecovery.data.db.RecoveredFileDao
import com.filerecovery.data.model.FileFilter
import com.filerecovery.data.model.RecoveredFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FileRecoveryUseCase(
    private val fileDao: RecoveredFileDao
) {

    fun getFilteredFiles(
        accountId: Long,
        filter: FileFilter
    ): Flow<List<RecoveredFile>> {
        return fileDao.observeFilesByAccount(accountId).map { files ->
            files.filter { file ->
                val typeMatch = filter.fileTypes.contains(file.fileType)
                val dateMatch = (filter.startDate == null || file.dateReceived >= filter.startDate) &&
                    (filter.endDate == null || file.dateReceived <= filter.endDate)
                val sizeMatch = file.fileSize in filter.minSize..filter.maxSize
                val duplicateMatch = !filter.excludeDuplicates || !file.isDuplicate
                val senderMatch = filter.senderEmail == null || file.senderEmail == filter.senderEmail

                typeMatch && dateMatch && sizeMatch && duplicateMatch && senderMatch
            }
        }
    }

    suspend fun detectDuplicates(accountId: Long) {
        val files = fileDao.getFilesByAccount(accountId)
        val hashGroups = files.groupBy { it.fileHash }

        for ((_, group) in hashGroups) {
            if (group.size > 1) {
                val original = group.first()
                for (i in 1 until group.size) {
                    fileDao.markAsDuplicate(group[i].id, original.id)
                }
            }
        }
    }

    suspend fun markAsDownloaded(fileId: Long, downloadPath: String) {
        fileDao.markAsDownloaded(fileId, downloadPath, "COMPLETED")
    }

    suspend fun deleteFile(fileId: Long) {
        fileDao.deleteFileById(fileId)
    }

    suspend fun toggleStarFile(file: RecoveredFile) {
        fileDao.updateRecoveredFile(file.copy(isStarred = !file.isStarred))
    }

    fun getStarredFiles(): Flow<List<RecoveredFile>> {
        return fileDao.observeStarredFiles()
    }

    fun getPendingFilesCount(accountId: Long): Flow<Int> {
        return fileDao.observePendingFileCount(accountId)
    }

    suspend fun getUndownloadedFiles(): List<RecoveredFile> {
        return fileDao.getUndownloadedFiles()
    }
}
