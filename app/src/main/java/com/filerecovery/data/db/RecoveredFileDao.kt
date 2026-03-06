package com.filerecovery.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.filerecovery.data.model.RecoveredFile
import kotlinx.coroutines.flow.Flow

@Dao
interface RecoveredFileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecoveredFile(file: RecoveredFile): Long

    @Update
    suspend fun updateRecoveredFile(file: RecoveredFile)

    @Delete
    suspend fun deleteRecoveredFile(file: RecoveredFile)

    @Query("SELECT * FROM recovered_files WHERE id = :id")
    suspend fun getFileById(id: Long): RecoveredFile?

    @Query("SELECT * FROM recovered_files WHERE emailAccountId = :accountId ORDER BY dateReceived DESC")
    suspend fun getFilesByAccount(accountId: Long): List<RecoveredFile>

    @Query("SELECT * FROM recovered_files WHERE emailAccountId = :accountId ORDER BY dateReceived DESC")
    fun observeFilesByAccount(accountId: Long): Flow<List<RecoveredFile>>

    @Query("SELECT * FROM recovered_files ORDER BY dateReceived DESC")
    suspend fun getAllRecoveredFiles(): List<RecoveredFile>

    @Query("SELECT * FROM recovered_files ORDER BY dateReceived DESC")
    fun observeAllRecoveredFiles(): Flow<List<RecoveredFile>>

    @Query("SELECT * FROM recovered_files WHERE fileType = :fileType AND emailAccountId = :accountId ORDER BY dateReceived DESC")
    suspend fun getFilesByType(fileType: String, accountId: Long): List<RecoveredFile>

    @Query("""
        SELECT * FROM recovered_files
        WHERE dateReceived >= :startDate
        AND dateReceived <= :endDate
        AND emailAccountId = :accountId
        ORDER BY dateReceived DESC
    """)
    suspend fun getFilesByDateRange(accountId: Long, startDate: Long, endDate: Long): List<RecoveredFile>

    @Query("SELECT * FROM recovered_files WHERE fileHash = :fileHash AND isDuplicate = 0 LIMIT 1")
    suspend fun getFileByHash(fileHash: String): RecoveredFile?

    @Query("SELECT * FROM recovered_files WHERE isDownloaded = 0 AND isDuplicate = 0 ORDER BY dateReceived DESC")
    suspend fun getUndownloadedFiles(): List<RecoveredFile>

    @Query("SELECT * FROM recovered_files WHERE isStarred = 1 ORDER BY dateReceived DESC")
    fun observeStarredFiles(): Flow<List<RecoveredFile>>

    @Query("UPDATE recovered_files SET isDownloaded = 1, downloadPath = :path, recovery_status = :status WHERE id = :id")
    suspend fun markAsDownloaded(id: Long, path: String, status: String)

    @Query("UPDATE recovered_files SET isDuplicate = 1, duplicateOf = :originalId WHERE id = :id")
    suspend fun markAsDuplicate(id: Long, originalId: Long)

    @Query("DELETE FROM recovered_files WHERE emailAccountId = :accountId")
    suspend fun deleteFilesByAccount(accountId: Long)

    @Query("DELETE FROM recovered_files WHERE id = :id")
    suspend fun deleteFileById(id: Long)

    @Query("SELECT COUNT(*) FROM recovered_files WHERE emailAccountId = :accountId AND isDownloaded = 0 AND isDuplicate = 0")
    fun observePendingFileCount(accountId: Long): Flow<Int>
}
