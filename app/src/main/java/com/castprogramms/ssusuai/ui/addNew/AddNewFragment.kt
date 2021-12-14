package com.castprogramms.ssusuai.ui.addNew

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentAddNewsBinding

class AddNewFragment : Fragment(R.layout.fragment_add_news) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddNewsBinding.bind(view)
    }
}