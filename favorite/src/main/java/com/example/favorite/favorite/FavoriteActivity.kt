package com.example.favorite.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.core.core.ui.AgentOnlyFavoriteAdapter
import com.example.favorite.databinding.ActivityFavoriteBinding
import com.example.projectsubmissionandroidexpert.detail.DetailAgentActivity
import com.example.projectsubmissionandroidexpert.di.FavoriteModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels  {
        factory
    }
    private lateinit var lottieAnimationView: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lottieAnimationView = binding.lottieAnimationView
        val favoriteComponent = DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
        favoriteComponent.inject(this)
        val adapterFavorite = AgentOnlyFavoriteAdapter()
        adapterFavorite.onItemClick = { selectedData->
            val intent = Intent(this, DetailAgentActivity::class.java).apply {
                putExtra(DetailAgentActivity.EXTRA_DATA, selectedData)
            }
            startActivity(intent)
        }
        lifecycleScope.launch {
            favoriteViewModel.getFavorite.collect { dataAgent ->
                adapterFavorite.setData(dataAgent)
                if (dataAgent.isEmpty()) {
                    lottieAnimationView.visibility = View.VISIBLE
                    lottieAnimationView.playAnimation()
                } else {
                    lottieAnimationView.visibility = View.GONE
                    lottieAnimationView.pauseAnimation()
                }
            }
        }
        with(binding.rvListAgentss) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = adapterFavorite
        }
    }
}