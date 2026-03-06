package com.filerecovery.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filerecovery.data.db.EmailAccountDao
import com.filerecovery.data.db.RecoveredFileDao
import com.filerecovery.data.model.EmailAccount
import com.filerecovery.data.model.FileFilter
import com.filerecovery.data.model.FileType
import com.filerecovery.data.model.RecoveredFile
import com.filerecovery.domain.usecase.FileRecoveryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FileRecoveryViewModel(
    private val emailAccountDao: EmailAccountDao,
    private val fileDao: RecoveredFileDao,
    private val fileRecoveryUseCase: FileRecoveryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FileRecoveryUiState>(FileRecoveryUiState.Loading)
    val uiState: StateFlow<FileRecoveryUiState> = _uiState.asStateFlow()

    private val _selectedAccount = MutableStateFlow<EmailAccount?>(null)
    val selectedAccount: StateFlow<EmailAccount?> = _selectedAccount.asStateFlow()

    private val _currentFilter = MutableStateFlow<FileFilter>(FileFilter())
    val currentFilter: StateFlow<FileFilter> = _currentFilter.asStateFlow()

    private val _recoveredFiles = MutableStateFlow<List<RecoveredFile>>(emptyList())
    val recoveredFiles: StateFlow<List<RecoveredFile>> = _recoveredFiles.asStateFlow()

    init {
        loadEmailAccounts()
    }

    private fun loadEmailAccounts() {
        viewModelScope.launch {
            try {
                val accounts = emailAccountDao.getAllEmailAccounts()
                if (accounts.isNotEmpty()) {
                    _selectedAccount.value = accounts.first()
                    loadFilesForAccount(accounts.first().id)
                } else {
                    _uiState.value = FileRecoveryUiState.NoAccounts
                }
            } catch (e: Exception) {
                _uiState.value = FileRecoveryUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun loadFilesForAccount(accountId: Long) {
        viewModelScope.launch {
            try {
                fileRecoveryUseCase.getFilteredFiles(accountId, _currentFilter.value).collect { files ->
                    _recoveredFiles.value = files
                    _uiState.value = FileRecoveryUiState.Success
                }
            } catch (e: Exception) {
                _uiState.value = FileRecoveryUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun selectAccount(account: EmailAccount) {
        _selectedAccount.value = account
        loadFilesForAccount(account.id)
    }

    fun applyFilter(filter: FileFilter) {
        _currentFilter.value = filter
        _selectedAccount.value?.let { account ->
            loadFilesForAccount(account.id)
        }
    }

    fun searchFiles(query: String) {
        viewModelScope.launch {
            val filtered = _recoveredFiles.value.filter { file ->
                file.fileName.contains(query, ignoreCase = true) ||
                file.senderEmail.contains(query, ignoreCase = true) ||
                file.messageSubject.contains(query, ignoreCase = true)
            }
            _recoveredFiles.value = filtered
        }
    }

    fun detectDuplicates() {
        viewModelScope.launch {
            try {
                _selectedAccount.value?.let { account ->
                    fileRecoveryUseCase.detectDuplicates(account.id)
                }
            } catch (e: Exception) {
                _uiState.value = FileRecoveryUiState.Error(e.message ?: "Failed to detect duplicates")
            }
        }
    }

    fun markFileAsDownloaded(fileId: Long, downloadPath: String) {
        viewModelScope.launch {
            try {
                fileRecoveryUseCase.markAsDownloaded(fileId, downloadPath)
            } catch (e: Exception) {
                _uiState.value = FileRecoveryUiState.Error(e.message ?: "Failed to mark file")
            }
        }
    }

    fun deleteFile(fileId: Long) {
        viewModelScope.launch {
            try {
                fileRecoveryUseCase.deleteFile(fileId)
            } catch (e: Exception) {
                _uiState.value = FileRecoveryUiState.Error(e.message ?: "Failed to delete file")
            }
        }
    }

    fun toggleStarFile(file: RecoveredFile) {
        viewModelScope.launch {
            try {
                fileRecoveryUseCase.toggleStarFile(file)
            } catch (e: Exception) {
                _uiState.value = FileRecoveryUiState.Error(e.message ?: "Failed to toggle star")
            }
        }
    }
}

sealed class FileRecoveryUiState {
    object Loading : FileRecoveryUiState()
    object Success : FileRecoveryUiState()
    object NoAccounts : FileRecoveryUiState()
    data class Error(val message: String) : FileRecoveryUiState()
}
