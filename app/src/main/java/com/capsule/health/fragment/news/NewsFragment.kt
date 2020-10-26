package com.capsule.health.fragment.news

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.capsule.health.R
import com.capsule.health.adapters.ConceptAdapter
import com.capsule.health.adapters.articles.ArticleAdapter
import com.capsule.health.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val conceptAdapter = ConceptAdapter {
        newsViewModel.selectConcept(it)
    }

    private val articleAdapter = ArticleAdapter {
        CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
            .build()
            .launchUrl(
                requireContext(),
                Uri.parse(it.url)
            )
    }

    private val newsViewModel: NewsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            conceptsAdapter = conceptAdapter
            articlesAdapter = articleAdapter
            viewModel = newsViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.concepts.observe(viewLifecycleOwner, Observer {
            it.data?.let { concepts ->
                val animationController: LayoutAnimationController =
                    AnimationUtils.loadLayoutAnimation(context, R.anim.slide_in)
                conceptsRecyclerView.layoutAnimation = animationController
                conceptAdapter.submitList(concepts)
            } ?: it.error?.let { _ ->
                Toast.makeText(
                    requireContext(),
                    it.message ?: getString(R.string.something_went_wrong),
                    LENGTH_SHORT
                ).show()
            }
        })

        val animationController: LayoutAnimationController by lazy {
            AnimationUtils.loadLayoutAnimation(
                requireContext(),
                R.anim.fall_in
            )
        }

        newsViewModel.articles.observe(viewLifecycleOwner, Observer {
            articlesRecyclerView.layoutAnimation = animationController
            articleAdapter.submitList(it)
        })
    }
}