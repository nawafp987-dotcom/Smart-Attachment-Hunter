package com.filerecovery.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.filerecovery.service.EmailSyncService

class SyncReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connMgr.activeNetworkInfo
                if (networkInfo != null && networkInfo.isConnected) {
                    val syncIntent = Intent(context, EmailSyncService::class.java)
                    context.startService(syncIntent)
                }
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                val syncIntent = Intent(context, EmailSyncService::class.java)
                context.startService(syncIntent)
            }
        }
    }
}
