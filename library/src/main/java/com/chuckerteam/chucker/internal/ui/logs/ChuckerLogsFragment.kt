package com.chuckerteam.chucker.internal.ui.logs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.databinding.ChuckerLogsFragmentBinding
import com.chuckerteam.chucker.internal.data.model.DialogData
import com.chuckerteam.chucker.internal.support.showDialog
import com.chuckerteam.chucker.internal.ui.MainViewModel


internal class ChuckerLogsFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var chuckerLogsAdapter: ChuckerLogsAdapter

    private lateinit var binding: ChuckerLogsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = ChuckerLogsFragmentBinding.inflate(inflater, container, false)
        chuckerLogsAdapter = ChuckerLogsAdapter()

        with(binding) {
            logsRecyclerView.apply {
                setHasFixedSize(true)
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                adapter = chuckerLogsAdapter
            }
        }

        viewModel.getAllLogs()
        viewModel.chuckerLogs.observeForever {
            binding.logsRecyclerView.adapter = chuckerLogsAdapter
            chuckerLogsAdapter.submitList(it)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chucker_logs_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear -> {
                requireContext().showDialog(
                        getClearDialogData(),
                        onPositiveClick = {
                            viewModel.clearLogs()
                        },
                        onNegativeClick = null
                )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
    private fun getClearDialogData(): DialogData = DialogData(
            title = getString(R.string.chucker_clear),
            message = getString(R.string.chucker_clear_http_confirmation),
            positiveButtonText = getString(R.string.chucker_clear),
            negativeButtonText = getString(R.string.chucker_cancel)
    )

    companion object {
        fun newInstance() = ChuckerLogsFragment()
    }
}