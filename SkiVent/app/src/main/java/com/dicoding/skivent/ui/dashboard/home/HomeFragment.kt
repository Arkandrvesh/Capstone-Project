package com.dicoding.skivent.ui.dashboard.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dicoding.skivent.R
import com.dicoding.skivent.databinding.FragmentHomeBinding
import com.dicoding.skivent.dataclass.HistoryItemItem


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        viewModel.getHistory()
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeViewModel() {
        viewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            if (historyList != null) {
                setHistoryData(historyList)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                showError(error)
            }
        }
    }

    private fun setHistoryData(historyList: List<HistoryItemItem>) {
        val adapter = HistoryAdapter(historyList)
        binding.rvHistory.adapter = adapter
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onRefresh() {
        binding.swipeRefresh.isRefreshing = true
//        rvAdapter.refresh()
//        Timer().schedule(2000) {
//            binding.swipeRefresh.isRefreshing = false
//            binding.rvStory.smoothScrollToPosition(0)
//        }
    }
}