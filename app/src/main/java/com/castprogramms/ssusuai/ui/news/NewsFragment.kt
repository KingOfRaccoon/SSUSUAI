package com.castprogramms.ssusuai.ui.news

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentNewsBinding
import com.castprogramms.ssusuai.databinding.FragmentNewsMotionBinding
import com.castprogramms.ssusuai.databinding.ItemNewsBinding
import com.castprogramms.ssusuai.tools.New
import com.castprogramms.ssusuai.tools.ui.BounceEdgeEffectFactory

class NewsFragment : Fragment(R.layout.fragment_news), NewsClickCallback {
    lateinit var binding: FragmentNewsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Лента новостей")
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding = FragmentNewsBinding.bind(view)
        binding.recyclerNews.adapter = NewsAdapter(this)
        binding.recyclerNews.edgeEffectFactory = BounceEdgeEffectFactory()
        binding.recyclerNews.apply {
            postponeEnterTransition()
            doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
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