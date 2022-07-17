package com.chuckerteam.chucker.internal.ui.logs

import com.chuckerteam.chucker.internal.data.entity.ChuckerLogs
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


public object ChuckerLogs {

    private val scope = MainScope()

    public fun debug(tag: String, message: Any?) {
        // store in DB
        scope.launch {
            try {
                val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                RepositoryProvider.transaction().insertChuckLogs(ChuckerLogs(
                        null, tag, message.toString(), currentTime
                ))
            } catch (ex: Exception) {
            }
        }
    }
}