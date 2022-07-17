package com.chuckerteam.chucker.internal.ui.logs

import com.chuckerteam.chucker.internal.data.entity.ChuckerLogs
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

public object ChuckerDebug {

    private val scope = MainScope()

    public fun debug(tag: String, message: Any?) {
        // store in DB
        scope.launch {
            try {
                RepositoryProvider.transaction().insertChuckLogs(ChuckerLogs(
                    null, tag, message.toString(), "time"
                ))
            } catch (ex: Exception) {

            }

        }
    }
}

