package com.chuckerteam.chucker.internal.ui.logs


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chuckerteam.chucker.databinding.ChuckerLogsListItemBinding
import com.chuckerteam.chucker.internal.data.entity.ChuckerLogs
import com.chuckerteam.chucker.internal.support.ChuckerLogsDiffCallback

internal class ChuckerLogsAdapter internal constructor(
) : ListAdapter<ChuckerLogs, ChuckerLogsAdapter.TransactionViewHolder>(ChuckerLogsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val viewBinding = ChuckerLogsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) =
            holder.bind(getItem(position))

    inner class TransactionViewHolder(
            private val itemBinding: ChuckerLogsListItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private var transactionId: Long? = null


        @SuppressLint("SetTextI18n")
        fun bind(logs: ChuckerLogs) {
            transactionId = logs.id

            itemBinding.apply {
                tag.text = logs.tag
                message.text = logs.message
                time.text = logs.time
            }

        }

    }
}