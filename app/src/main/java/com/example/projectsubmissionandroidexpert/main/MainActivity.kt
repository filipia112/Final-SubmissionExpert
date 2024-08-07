package com.example.projectsubmissionandroidexpert.main

import android.app.ActivityOptions
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.core.data.source.Resource
import com.example.core.core.ui.AgentAdapter
import com.example.projectsubmissionandroidexpert.detail.DetailAgentActivity
import com.example.projectsubmissionandroidexpert.R
import com.example.projectsubmissionandroidexpert.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val searchViewModel: SearchViewModel by viewModels ()
    private val listAgentsViewModel: ListAgentViewModel by viewModels ()
    private lateinit var binding: ActivityMainBinding
    private lateinit var broadcastReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val agentsAdapter = AgentAdapter()
        agentsAdapter.onItemClick = { selectedData, sharedElement ->
            val intent = Intent(this, DetailAgentActivity::class.java).apply {
                putExtra(DetailAgentActivity.EXTRA_DATA, selectedData)
            }
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                sharedElement,
                "sharedImageTransition"
            )
            startActivity(intent, options.toBundle())
        }
        registerBroadCastReceiver()
        listAgentsViewModel.agent.observe(this) { agent ->
            if (agent != null) {
                when (agent) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        agentsAdapter.setData(agent.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            agent.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
        searchViewModel.filteredAgentsLiveData.observe(this) { filteredAgents ->
            agentsAdapter.setData(filteredAgents)
        }
        binding.searchViewAgents.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.search(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchViewModel.search(it)
                }
                return true
            }
        })
        with(binding.rvListAgentss) {
            layoutManager =LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = agentsAdapter
        }
        binding.btnNavigateFavorite.setOnClickListener{
            activityfavorite()
        }
    }
    private fun activityfavorite() {
        val uri = Uri.parse("projectsubmissionandroidexpert://favorite")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
    private fun registerBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_POWER_CONNECTED -> {
                        binding.tvPowerStatus.text = getString(R.string.power_connected)
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
                        binding.tvPowerStatus.text = getString(R.string.power_disconnected)
                    }
                }
            }
        }
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

}