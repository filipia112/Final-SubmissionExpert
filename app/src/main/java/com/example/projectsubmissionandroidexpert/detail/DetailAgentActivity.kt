package com.example.projectsubmissionandroidexpert.detail
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.core.core.domain.model.Agent
import com.example.projectsubmissionandroidexpert.R
import com.example.projectsubmissionandroidexpert.databinding.ActivityDetailAgentBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class DetailAgentActivity : AppCompatActivity() {
    private val detailAgentViewModel: DetailAgentViewModel by viewModels ()
    private lateinit var binding: ActivityDetailAgentBinding
    companion object {
        const val EXTRA_DATA = "VIEW_DETAIL_AGENT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAgentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailAgent = intent.getParcelableExtra<Agent>(EXTRA_DATA)
        detailAgent?.let {
            showDetailAgent(it)
        }
        val transitionInflater = TransitionInflater.from(this)
        window.sharedElementEnterTransition = transitionInflater.inflateTransition(R.transition.shared_element_enter_transition)
        window.sharedElementReturnTransition = transitionInflater.inflateTransition(R.transition.shared_element_return_transition)
        detailAgentViewModel.favoriteStatus.observe(this) { isFavorite ->
            binding.toggleButton.isChecked = isFavorite
        }
        binding.btnSearchInYoutube.setOnClickListener {
            detailAgent?.let { agent ->
                navigateToYoutube(agent)
            }
        }
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun navigateToYoutube (detailAgent: Agent?) {
        val query = detailAgent?.displayName
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query= How to Use a Hero Character $query in Valorant"))
        startActivity(intent)
    }
    private fun showDetailAgent(detailAgent: Agent?) {
        detailAgent?.let { agent ->
            binding.tvNameAgents.text = agent.displayName
            Glide.with(this)
                .load(agent.background)
                .into(binding.IvbackgroundTextName)

            Glide.with(this)
                .load(agent.fullPortrait)
                .into(binding.IvagentsImage)
            val combinetext = agent.developerName
            val gradientBackground = agent.backgroundGradientColors
            val gradientDrawable = gradientBackground?.let { parseHexColors(it) }
            binding.backgroundGradient.setImageDrawable(gradientDrawable)
            binding.tvNameDevelopers.text = getString(R.string.developer_name, combinetext)
            binding.tvNameAgents2.text = agent.displayName
            binding.tvDescription.text = agent.description
            setStatusFavorite(agent.isFavorite)
            binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
                detailAgentViewModel.setFavoriteAgent(agent, isChecked)
            }
            fadeIn(binding.backgroundGradient)
            binding.backgroundGradient.postDelayed({
            }, 3000)
        }
    }
    private fun setStatusFavorite(statusFavorite: Boolean) {
        binding.toggleButton.isChecked = statusFavorite
    }
    private fun parseHexColors(hexString: String): Drawable {
        val colors = hexString.split(",")
            .map { it.trim() }
            .map { android.graphics.Color.parseColor("#$it") }

        return createGradientDrawable(colors)
    }
    private fun createGradientDrawable(colors: List<Int>): Drawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadius = 32f
        gradientDrawable.colors = colors.toIntArray()
        gradientDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        return gradientDrawable
    }
    private fun fadeIn(view: ImageView) {
        val fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        fadeIn.duration = 1500
        fadeIn.start()
    }
}