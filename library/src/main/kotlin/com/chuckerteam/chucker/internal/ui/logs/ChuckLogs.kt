package com.chuckerteam.chucker.internal.ui.logs

import android.content.Context
import com.chuckerteam.chucker.internal.data.entity.ChuckerLogs
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

public object ChuckerDebug {

    private val scope = MainScope()

    public fun debug(tag: String, message: Any?, context: Context) {
        // store in DB
        scope.launch {
            RepositoryProvider.initialize(applicationContext = context)
            RepositoryProvider.transaction().insertChuckLogs(ChuckerLogs(
                null, tag, message.toString(), "time"
            ))
        }
    }
}

