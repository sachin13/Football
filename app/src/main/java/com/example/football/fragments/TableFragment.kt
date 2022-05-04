package com.example.football.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.football.adapter.TeamsRvAdapter
import com.example.football.database.AppDatabase
import com.example.football.databinding.FragmentTableBinding
import com.example.football.utils.NetworkHelper
import com.example.football.utils.StandingsResource
import com.example.football.utils.hide
import com.example.football.utils.show
import com.example.football.viewmodels.ViewModel
import com.example.football.viewmodels.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "param1"

class TableFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    private lateinit var binding: FragmentTableBinding
    private lateinit var viewModel: ViewModel
    private lateinit var appDatabase: AppDatabase
    private lateinit var teamsRvAdapter: TeamsRvAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTableBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())

        viewModel = ViewModelProvider(this,
            ViewModelFactory(appDatabase, NetworkHelper(requireContext()))
        )[ViewModel::class.java]

        viewModel.fetchStandingsData(param1!!)

        teamsRvAdapter = TeamsRvAdapter()


        binding.apply { recyclerView.adapter = teamsRvAdapter }


        lifecycleScope.launch {
            viewModel.getStandingsData().collect {
                when (it) {
                    is StandingsResource.Loading -> {
                        binding.loading.show()
                        binding.recyclerView.hide()
                    }
                    is StandingsResource.Error -> {
                        binding.recyclerView.hide()
                        binding.loading.hide()
                    }

                    is StandingsResource.Succes -> {
                        teamsRvAdapter.submitList(it.data.data.standings)
                        binding.recyclerView.show()
                        binding.loading.hide()
                    }
                }
            }
        }




        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            TableFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}