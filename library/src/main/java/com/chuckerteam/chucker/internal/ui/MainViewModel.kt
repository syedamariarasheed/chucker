package com.chuckerteam.chucker.internal.ui

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.chuckerteam.chucker.internal.data.entity.ChuckerLogs
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.data.entity.RecordedThrowableTuple
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider
import com.chuckerteam.chucker.internal.support.NotificationHelper
import kotlinx.coroutines.launch

internal class MainViewModel : ViewModel() {

    private val currentFilter = MutableLiveData<String>("")

    private var _chuckerLogs = MutableLiveData<List<ChuckerLogs>>(null)
    val chuckerLogs: LiveData<List<ChuckerLogs>> = _chuckerLogs

    val transactions: LiveData<List<HttpTransactionTuple>> = currentFilter.switchMap { searchQuery ->
        with(RepositoryProvider.transaction()) {
            when {
                searchQuery.isNullOrBlank() -> {
                    getSortedTransactionTuples()
                }
                TextUtils.isDigitsOnly(searchQuery) -> {
                    getFilteredTransactionTuples(searchQuery, "")
                }
                else -> {
                    getFilteredTransactionTuples("", searchQuery)
                }
            }
        }
    }

    val throwables: LiveData<List<RecordedThrowableTuple>> = RepositoryProvider.throwable()
        .getSortedThrowablesTuples()

    suspend fun getAllTransactions(): List<HttpTransaction>? = RepositoryProvider.transaction().getAllTransactions()

    fun updateItemsFilter(searchQuery: String) {
        currentFilter.value = searchQuery
    }

    fun clearTransactions() {
        viewModelScope.launch {
            RepositoryProvider.transaction().deleteAllTransactions()
        }
        NotificationHelper.clearBuffer()
    }

    fun clearLogs() {
        viewModelScope.launch {
            RepositoryProvider.transaction().deleteAllLogs()
        }.invokeOnCompletion {
            _chuckerLogs.value = emptyList()
        }
    }

    fun clearThrowables() {
        viewModelScope.launch {
            RepositoryProvider.throwable().deleteAllThrowables()
        }
    }

    fun getAllLogs() {
        viewModelScope?.launch {
            _chuckerLogs.value = RepositoryProvider.transaction().getAllLogs()
        }

    }
}
