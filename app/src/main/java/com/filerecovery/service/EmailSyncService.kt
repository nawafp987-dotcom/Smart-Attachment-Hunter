package com.filerecovery.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.filerecovery.R
import com.filerecovery.data.db.EmailAccountDao
import com.filerecovery.data.db.RecoveredFileDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class EmailSyncService : Service() {

    private val binder = EmailSyncBinder()
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private lateinit var emailAccountDao: EmailAccountDao
    private lateinit var fileDao: RecoveredFileDao

    override fun onBind(intent: Intent): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, "sync_channel")
            .setContentTitle("File Recovery")
            .setContentText("Syncing email accounts...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setProgress(100, 0, false)
            .build()

        startForeground(2, notification)

        scope.launch {
            syncAllAccounts()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private suspend fun syncAllAccounts() {
        try {
            val accounts = emailAccountDao.getLinkedAccounts()
            for (account in accounts) {
                emailAccountDao.updateSyncStatus(account.id, "SYNCING", System.currentTimeMillis())
            }
            emailAccountDao.updateSyncStatus(accounts.firstOrNull()?.id ?: 0, "COMPLETED", System.currentTimeMillis())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class EmailSyncBinder : Binder() {
        fun getService(): EmailSyncService = this@EmailSyncService
    }

    fun triggerSync() {
        scope.launch {
            syncAllAccounts()
        }
    }
}
