package com.castprogramms.ssusuai.ui.news

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentNewsBinding
import com.castprogramms.ssusuai.databinding.ItemNewsBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.New
import com.castprogramms.ssusuai.tools.ui.BounceEdgeEffectFactory
import com.castprogramms.ssusuai.users.Admin
import com.castprogramms.ssusuai.users.CommonUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(R.layout.fragment_news), NewsClickCallback {
    lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Лента новостей")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity?)?.slideUp()
        binding = FragmentNewsBinding.bind(view)
        binding.recyclerNews.adapter = NewsAdapter(this)
        binding.recyclerNews.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerNews.apply {
            postponeEnterTransition()
            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
        val userId = GoogleSignIn.getLastSignedInAccount(requireContext()).id
        viewModel.getUser(userId).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (userId != null) {
                        when (it.data) {
                            is CommonUser -> {
                                setHasOptionsMenu(false)
                            }
                            is Admin -> {
                                setHasOptionsMenu(true)
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_news_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add_news -> {
                findNavController().navigate(R.id.action_newsFragment_to_addNewsFragment)
            }
        }
        return true
    }

    override fun clickOnNews(position: Int, binding: ItemNewsBinding, new: New) {
        val bundle = Bundle()
        bundle.putSerializable("new", new)
        bundle.putString("img_card_name", "img" + new.title.split(" ").first())
        ViewCompat.setTransitionName(binding.imageNewInCard, "img" + new.title.split(" ").first())
        val extra = FragmentNavigator.Extras.Builder()
            .addSharedElement(binding.imageNewInCard, binding.imageNewInCard.transitionName)
            .build()

        findNavController().navigate(
            R.id.action_newsFragment_to_newFragment, bundle, null, extra
        )
//        this.binding.motion.transitionToEnd()
    }
}