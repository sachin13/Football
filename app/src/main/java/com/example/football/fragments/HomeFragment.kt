package com.example.football.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.football.R
import com.example.football.adapter.LeagueDatasRvAdapter
import com.example.football.database.AppDatabase
import com.example.football.database.entities.LeaguesEntity
import com.example.football.databinding.FragmentHomeBinding
import com.example.football.utils.NetworkHelper
import com.example.football.viewmodels.ViewModel
import com.example.football.viewmodels.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ViewModel
    private val job = Job()
    private lateinit var appDatabase: AppDatabase
    private lateinit var leagueDatasRvAdapter: LeagueDatasRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getInstance(requireContext())
        viewModel = ViewModelProvider(this,
            ViewModelFactory(appDatabase, NetworkHelper(requireContext()))
        )[ViewModel::class.java]

        leagueDatasRvAdapter =
            LeagueDatasRvAdapter(object : LeagueDatasRvAdapter.OnItemClickListener {
                override fun onItemClickListener(item: LeaguesEntity) {
                    val bundle = Bundle()
                    bundle.putString("id", item.id)
                    findNavController().navigate(R.id.leagueFragment, bundle)
                }

            }, appDatabase)

        leagueDatasRvAdapter.submitList(appDatabase.leaguesDao().getAllLeaguesData())

        binding.recyclerView.adapter = leagueDatasRvAdapter

        return binding.root

    }

    override val coroutineContext: CoroutineContext
        get() = job


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}