package com.example.football.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.football.R
import com.example.football.database.AppDatabase
import com.example.football.databinding.FragmentSplashBinding

import com.example.football.utils.LeaguesResource
import com.example.football.utils.NetworkHelper
import com.example.football.utils.StandingsResource
import com.example.football.viewmodels.ViewModel
import com.example.football.viewmodels.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashFragment : Fragment() {


    private lateinit var binding: FragmentSplashBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: ViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        appDatabase = AppDatabase.getInstance(requireContext())

        viewModel = ViewModelProvider(this,
            ViewModelFactory(appDatabase, NetworkHelper(requireContext()))
        )[ViewModel::class.java]

        viewModel.fetchLeaguesData()

        lifecycleScope.launch {
            viewModel.getLeagueData().collect {
                when (it) {
                    is LeaguesResource.Error -> {
                        binding.root.setBackgroundColor(Color.RED)
                    }

                    is LeaguesResource.Success -> {
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
        }



        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun writeToDbStandings(index: Int) {
        val list = appDatabase.leaguesDao().getAllLeaguesData()

        if (index == list.size - 1) {
        } else {
            viewModel.fetchStandingsData(list[index].id)
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    viewModel.getStandingsData().collect {

                        when (it) {
                            is StandingsResource.Loading -> {

                            }

                            is StandingsResource.Error -> {

                            }

                            is StandingsResource.Succes -> {
                                writeToDbStandings(index + 1)
                            }
                        }
                    }
                }
            }
        }

    }

}