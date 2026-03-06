package com.filerecovery.util

import android.content.Context
import android.widget.Toast
import com.filerecovery.R

sealed class AppError(
    open val message: String,
    open val cause: Throwable? = null
) {
    data class NetworkError(
        override val message: String = "فشل الاتصال بالإنترنت",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class AuthenticationError(
        override val message: String = "خطأ في المصادقة",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class DatabaseError(
        override val message: String = "خطأ في قاعدة البيانات",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class FileAccessError(
        override val message: String = "لا يمكن الوصول للملف",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class PermissionError(
        override val message: String = "الأذونات غير كافية",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class StorageQuotaExceeded(
        override val message: String = "تم تجاوز حد التخزين",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class DuplicateFileError(
        override val message: String = "الملف مكرر",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    data class UnknownError(
        override val message: String = "حدث خطأ غير متوقع",
        override val cause: Throwable? = null
    ) : AppError(message, cause)
}

class ErrorHandler(private val context: Context) {

    fun handleError(error: AppError) {
        when (error) {
            is AppError.NetworkError -> showError(error.message)
            is AppError.AuthenticationError -> showError(error.message)
            is AppError.DatabaseError -> showError(error.message)
            is AppError.FileAccessError -> showError(error.message)
            is AppError.PermissionError -> showError(error.message)
            is AppError.StorageQuotaExceeded -> showError(error.message)
            is AppError.DuplicateFileError -> showError(error.message)
            is AppError.UnknownError -> showError(error.message)
        }
    }

    fun handleException(throwable: Throwable): AppError {
        return when (throwable) {
            is java.io.IOException -> AppError.NetworkError(cause = throwable)
            is java.nio.file.AccessDeniedException -> AppError.FileAccessError(cause = throwable)
            is SecurityException -> AppError.PermissionError(cause = throwable)
            else -> AppError.UnknownError(cause = throwable)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun logError(tag: String, error: AppError) {
        android.util.Log.e(tag, error.message, error.cause)
    }

    fun logException(tag: String, throwable: Throwable) {
        android.util.Log.e(tag, "Exception: ${throwable.message}", throwable)
    }
}

object ErrorMessages {
    const val NETWORK_ERROR = "فشل الاتصال. تحقق من الإنترنت"
    const val AUTH_ERROR = "فشل التحقق من الهوية. حاول تسجيل الدخول مجدداً"
    const val DB_ERROR = "خطأ في البيانات. حاول لاحقاً"
    const val FILE_ERROR = "خطأ في معالجة الملف"
    const val PERMISSION_ERROR = "الرجاء منح الأذونات المطلوبة"
    const val STORAGE_FULL = "المساحة المتاحة غير كافية"
    const val SYNC_FAILED = "فشلت المزامنة. حاول مرة أخرى"
    const val DOWNLOAD_FAILED = "فشل التحميل"
    const val UPLOAD_FAILED = "فشل الرفع"
}
