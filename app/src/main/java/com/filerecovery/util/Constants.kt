package com.filerecovery.util

object Constants {
    // API Configuration
    const val GMAIL_API_BASE_URL = "https://www.googleapis.com/gmail/v1/"
    const val OUTLOOK_API_BASE_URL = "https://graph.microsoft.com/v1.0/"
    const val OAUTH_CALLBACK_URL = "http://localhost:8080/oauth2callback"

    // Database Configuration
    const val DB_NAME = "file_recovery_db"
    const val DB_VERSION = 1

    // Notification Configuration
    const val SYNC_NOTIFICATION_ID = 1
    const val DOWNLOAD_NOTIFICATION_ID = 2
    const val SYNC_CHANNEL_ID = "sync_channel"
    const val DOWNLOAD_CHANNEL_ID = "download_channel"

    // Preferences
    const val SHARED_PREF_NAME = "file_recovery_prefs"
    const val PREF_TOKEN_ENCRYPTION_KEY = "token_encryption_key"
    const val PREF_LAST_SYNC_TIME = "last_sync_time"
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_EMAIL = "user_email"

    // File Configuration
    const val MAX_FILE_SIZE = 2 * 1024 * 1024 * 1024L // 2 GB
    const val THUMBNAIL_WIDTH = 256
    const val THUMBNAIL_HEIGHT = 256
    const val THUMBNAIL_QUALITY = 80

    // Storage
    const val DOWNLOADS_FOLDER = "FileRecovery/Downloads"
    const val CACHE_FOLDER = "FileRecovery/Cache"
    const val TEMP_FOLDER = "FileRecovery/Temp"

    // Sync Configuration
    const val SYNC_INTERVAL = 60 * 60 * 1000L // 1 hour in milliseconds
    const val SYNC_TIMEOUT = 5 * 60 * 1000L // 5 minutes

    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_LOAD_SIZE = 50

    // Email Batch Size
    const val EMAIL_BATCH_SIZE = 100
    const val MAX_EMAILS_PER_REQUEST = 100

    // Date Range Limits
    const val MIN_YEAR = 2000
    const val MAX_YEAR_OFFSET = 1 // years from now

    // Security
    const val ENCRYPTION_ALGORITHM = "AES"
    const val ENCRYPTION_KEY_LENGTH = 256
    const val TOKEN_REFRESH_BUFFER = 5 * 60 * 1000L // 5 minutes before expiry

    // Retry Configuration
    const val MAX_RETRY_ATTEMPTS = 3
    const val RETRY_DELAY = 1000L // milliseconds
    const val RETRY_BACKOFF_MULTIPLIER = 2.0

    // Network Timeout
    const val CONNECT_TIMEOUT = 30L // seconds
    const val READ_TIMEOUT = 30L // seconds
    const val WRITE_TIMEOUT = 30L // seconds

    // API Rate Limiting
    const val RATE_LIMIT_REQUESTS = 100
    const val RATE_LIMIT_WINDOW = 60 * 1000L // 1 minute

    // Error Messages
    object ErrorMessages {
        const val NETWORK_ERROR = "Network connection failed"
        const val AUTH_ERROR = "Authentication failed"
        const val SYNC_ERROR = "Synchronization failed"
        const val DOWNLOAD_ERROR = "Download failed"
        const val UPLOAD_ERROR = "Upload failed"
        const val FILE_NOT_FOUND = "File not found"
        const val INVALID_FILE = "Invalid file"
        const val INSUFFICIENT_STORAGE = "Insufficient storage space"
    }

    // Intent Actions
    const val ACTION_SYNC_COMPLETE = "com.filerecovery.SYNC_COMPLETE"
    const val ACTION_DOWNLOAD_COMPLETE = "com.filerecovery.DOWNLOAD_COMPLETE"
    const val ACTION_ERROR = "com.filerecovery.ERROR"

    // Extra Keys
    const val EXTRA_ACCOUNT_ID = "account_id"
    const val EXTRA_FILE_ID = "file_id"
    const val EXTRA_ERROR_MESSAGE = "error_message"
    const val EXTRA_FILE_COUNT = "file_count"
    const val EXTRA_SYNC_TIME = "sync_time"
}

object TimeConstants {
    const val SECOND = 1000L
    const val MINUTE = 60 * SECOND
    const val HOUR = 60 * MINUTE
    const val DAY = 24 * HOUR
    const val WEEK = 7 * DAY
    const val MONTH = 30 * DAY
    const val YEAR = 365 * DAY
}

object PermissionConstants {
    val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_NETWORK_STATE,
        android.Manifest.permission.GET_ACCOUNTS
    )

    val STORAGE_PERMISSIONS = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val NETWORK_PERMISSIONS = arrayOf(
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.ACCESS_NETWORK_STATE
    )
}
