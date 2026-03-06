package com.filerecovery.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.filerecovery.data.model.EmailAccount
import com.filerecovery.data.model.RecoveredFile
import com.filerecovery.data.model.User

@Database(
    entities = [User::class, EmailAccount::class, RecoveredFile::class],
    version = 1,
    exportSchema = false
)
abstract class FileRecoveryDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun emailAccountDao(): EmailAccountDao
    abstract fun recoveredFileDao(): RecoveredFileDao

    companion object {
        @Volatile
        private var INSTANCE: FileRecoveryDatabase? = null

        fun getInstance(context: Context): FileRecoveryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FileRecoveryDatabase::class.java,
                    "file_recovery_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
