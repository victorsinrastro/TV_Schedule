package com.example.tvschedule.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvschedule.R
import com.example.tvschedule.data.EpisodeRepository
import com.example.tvschedule.data.database.TVScheduleDatabase
import com.example.tvschedule.data.database.EpisodeLocalRepository
import com.example.tvschedule.databinding.ActivityMainBinding
import com.example.tvschedule.data.network.ScheduleLoader
import com.example.tvschedule.ui.main.adapters.ChannelScheduleAdapter
import com.example.tvschedule.ui.main.adapters.HourButtonAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mainBinding.toolbar as Toolbar?)

        setUpViewModel()
        setupRecyclerView()
    }

    private fun setUpViewModel() {
        val database =TVScheduleDatabase.getDatabase(this)
        val tvScheduleLoader = ScheduleLoader()
        val episodeLocalRepository = EpisodeLocalRepository(database.episodeDao())
        val repository = EpisodeRepository(
            episodeLocalRepository,
            tvScheduleLoader
        )
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository)
        )[MainViewModel::class.java]
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val channelScheduleAdapter = ChannelScheduleAdapter(emptyList())
        val hourButtonAdapter = HourButtonAdapter(mainViewModel)

        mainBinding.apply {
            episodeAdapter = channelScheduleAdapter
            recyclerViewSchedule.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
            }

            mainBinding.hourButtonAdapter = hourButtonAdapter
            recyclerViewHours.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        mainViewModel.schedule.observe(this) { schedule ->
            mainBinding.episodeAdapter?.schedule = schedule
            mainBinding.episodeAdapter?.notifyDataSetChanged()
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            mainBinding.isProgressBarVisible = isLoading
        }

        mainViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}