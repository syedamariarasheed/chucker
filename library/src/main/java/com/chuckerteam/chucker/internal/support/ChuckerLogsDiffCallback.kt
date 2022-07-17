package com.chuckerteam.chucker.internal.support

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.chuckerteam.chucker.internal.data.entity.ChuckerLogs

internal object ChuckerLogsDiffCallback : DiffUtil.ItemCallback<ChuckerLogs>() {
    override fun areItemsTheSame(oldItem: ChuckerLogs, newItem: ChuckerLogs): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ChuckerLogs, newItem: ChuckerLogs): Boolean {
        return oldItem == newItem
    }

    // Overriding function is empty on purpose to avoid flickering by default animator
    override fun getChangePayload(oldItem: ChuckerLogs, newItem: ChuckerLogs) = Unit
}
