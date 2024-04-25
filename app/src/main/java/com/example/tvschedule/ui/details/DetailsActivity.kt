package com.example.tvschedule.ui.details

import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.tvschedule.R
import com.example.tvschedule.data.EpisodeRepository
import com.example.tvschedule.data.database.TVScheduleDatabase
import com.example.tvschedule.data.database.EpisodeLocalRepository
import com.example.tvschedule.data.network.ScheduleLoader
import com.example.tvschedule.databinding.ActivityDetailBinding
import com.example.tvschedule.ui.main.adapters.EPISODE

class DetailsActivity : AppCompatActivity() {
    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val episodeId = intent.getIntExtra(EPISODE, -1)
        setUpViewModel()
        detailViewModel.getEpisodeById(episodeId)
        observeViewModel()

    }

    private fun observeViewModel() {
        detailViewModel.episode.observe(this) { episode ->
            episode.let {
                detailBinding.show = episode?.show
                detailBinding.summaryTextView.text = Html.fromHtml(episode?.show?.summary ?: "", Html.FROM_HTML_MODE_LEGACY)
                setUpToolBar(episode?.show?.name)
                loadEpisodeImage(episode?.show?.image?.original)
            }
        }

        detailViewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            detailBinding.isProgressBarVisible = isLoading
        }
    }

    private fun loadEpisodeImage(imageUrl: String?) {
        Glide.with(this@DetailsActivity)
            .load(imageUrl)
            .into(detailBinding.showImageView)
    }

    private fun setUpViewModel() {
        val database = TVScheduleDatabase.getDatabase(this)
        val tvScheduleLoader = ScheduleLoader()
        val episodeLocalRepository = EpisodeLocalRepository(database.episodeDao())
        val repository = EpisodeRepository(
            episodeLocalRepository,
            tvScheduleLoader
        )
        detailViewModel = ViewModelProvider(
            this,
            DetailViewModelFactory(repository)
        )[DetailViewModel::class.java]
    }

    private fun setUpToolBar(episodeName: String?) {
        val toolbar = detailBinding.toolbar as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = episodeName
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}