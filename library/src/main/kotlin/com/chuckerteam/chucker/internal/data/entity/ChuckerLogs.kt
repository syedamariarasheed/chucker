@file:Suppress("TooManyFunctions")

package com.chuckerteam.chucker.internal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Represent a full HTTP transaction (with Request and Response). Instances of this classes
 * should be populated as soon as the library receives data from OkHttp.
 */
@Suppress("LongParameterList")
@Entity(tableName = "chucklogs")
internal class ChuckerLogs(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Long ?= 0,
    @ColumnInfo(name = "tag") var tag: String?,
    @ColumnInfo(name = "message") var message: String?,
    @ColumnInfo(name = "time") var time: String?,
) {

    @Ignore
    constructor() : this(
        tag = null,
        message = null,
        time = null
    )


}
