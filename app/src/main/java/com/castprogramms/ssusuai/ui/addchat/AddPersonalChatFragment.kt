package com.castprogramms.ssusuai.ui.addchat

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentAddPersonalChatBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.chat.TypeChats
import com.castprogramms.ssusuai.users.Person
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddPersonalChatFragment : Fragment(R.layout.fragment_add_personal_chat),
    AddPersonalChatCallback {
    val viewModel: AddPersonalChatViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Все пользователи")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setHasOptionsMenu(true)
        val binding = FragmentAddPersonalChatBinding.bind(view)
        val adapter = AddPersonalChatAdapter(this)
        binding.recyclerAddUsersPersonal.adapter = adapter

        viewModel.getCurrentUser().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (it.data != null) {
                        viewModel.getUsers(
                            it.data,
                            GoogleSignIn.getLastSignedInAccount(requireContext())?.id.toString()
                        )
                    }
                }
            }
        }

        viewModel.usersLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (it.data != null) {
                        adapter.setAddUsers(it.data)
                    }
                }
            }
        }
    }

    override fun addPersonalChat(pair: Pair<String, Person>) {
        val googleSignIn = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (googleSignIn?.id != null)
            viewModel.addPersonalChat(googleSignIn.id!!, pair).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            findNavController().navigate(
                                R.id.action_addPersonalChatFragment_to_chatFragment,
                                Bundle().apply {
                                    putString("idChat", it.data)
                                    putSerializable("typeChat", TypeChats.PersonalChat)
                                }
                            )
                        }
                    }
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            findNavController().popBackStack()
        return super.onOptionsItemSelected(item)
    }
}