package com.chuckerteam.chucker.internal.ui.logs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.databinding.ChuckerActivityLogsBinding
import com.chuckerteam.chucker.databinding.ChuckerActivityMainBinding
import com.chuckerteam.chucker.internal.ui.BaseChuckerActivity
import com.chuckerteam.chucker.internal.ui.MainViewModel
import com.chuckerteam.chucker.internal.ui.transaction.ChuckerLogsAdapter
import okhttp3.internal.notify

internal class LogsActivity : BaseChuckerActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var chuckerLogsAdapter: ChuckerLogsAdapter

    private lateinit var binding: ChuckerActivityLogsBinding

    private val applicationName: CharSequence
        get() = applicationInfo.loadLabel(packageManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChuckerActivityLogsBinding.inflate(layoutInflater)
        chuckerLogsAdapter = ChuckerLogsAdapter(this)
        with(binding) {
            setContentView(root)
            setSupportActionBar(toolbar)
            toolbar.subtitle = applicationName
            logsRecyclerView.apply {
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(this@LogsActivity, DividerItemDecoration.VERTICAL))
                adapter = chuckerLogsAdapter
            }
        }

        viewModel.getAllLogs()
        viewModel.chuckerLogs.observeForever {
            chuckerLogsAdapter.currentList.clear()
            binding.logsRecyclerView.adapter = chuckerLogsAdapter
            chuckerLogsAdapter.submitList(it)
        }
    }
}
