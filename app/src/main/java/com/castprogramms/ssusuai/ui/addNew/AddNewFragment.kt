package com.castprogramms.ssusuai.ui.addNew

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.castprogramms.ssusuai.MainActivity
import com.castprogramms.ssusuai.R
import com.castprogramms.ssusuai.databinding.FragmentAddNewsBinding
import com.castprogramms.ssusuai.repository.Resource
import com.castprogramms.ssusuai.tools.New
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.concurrent.timerTask

class AddNewFragment : Fragment(R.layout.fragment_add_news) {
    val viewModel: AddNewViewModel by viewModel()
    lateinit var binding: FragmentAddNewsBinding
    private var imageUri = Uri.EMPTY
    private val activityResultAddManyPhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            if (data != null && data.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i)?.uri
                    println(imageUri)
                }
            } else if (data != null) {
                val imageUri = data.data
                println("One")
                println(imageUri)
            }
        }
    }

    private val cropActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val data = CropImage.getActivityResult(it.data).uri
            imageUri = data
            setImage(imageUri)
        }
    }

    private val activityResultAddTitlePhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            imageUri = it.data?.data
            if (imageUri != null){
                cropActivityForResult.launch(
                    CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .getIntent(requireContext())
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setHtmlText("Добавление новости")
        binding = FragmentAddNewsBinding.bind(view)
        validate()
        if (imageUri != Uri.EMPTY){
            setImage(imageUri)
        }

        binding.addManyPhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            activityResultAddManyPhoto.launch(Intent.createChooser(intent, "Выберите фотографии"))
        }

        binding.imageTitleAddNew.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.putExtra("return-data", true)
            activityResultAddTitlePhoto.launch(intent)
        }

        binding.previewNewButton.setOnClickListener {
            if (checkEmpty()) {
                val new = New(
                    binding.titleText.text.toString(),
                    binding.bodyText.text.toString(),
                    imageUri.toString()
                )
                val bundle = Bundle()
                bundle.putSerializable("new", new)
                bundle.putString("img_card_name", "img" + new.title.split(" ").first())

                findNavController().navigate(R.id.action_addNewFragment_to_newFragment, bundle)
            }
        }

        binding.addNewButton.setOnClickListener {
            if (checkEmpty()){
                viewModel.addNew(
                    New(
                        binding.titleText.text.toString(),
                        binding.bodyText.text.toString(),
                        imageUri.toString()
                    )
                ).observe(viewLifecycleOwner){
                    when(it){
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            if (it.data != null){
                                goToAllNewsFragment()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun goToAllNewsFragment(){
        findNavController().popBackStack()
//        Timer().schedule(timerTask{ findNavController().popBackStack() }, 1000)
    }

    private fun setImage(image: Uri){
        binding.imageTitleAddNew.background = null
        binding.imageTitleAddNew.setPadding(0)
        Glide.with(requireContext())
            .load(image)
            .transform(CircleCrop())
            .into(binding.imageTitleAddNew)
    }

    private fun checkEmpty(): Boolean {
        val listChecks = mutableListOf<Boolean>()
        if (binding.titleText.text?.trim()?.isNotEmpty() != true) {
            listChecks.add(false)
            binding.titleText.error = "Введите заголовок"
        }

        if (binding.bodyText.text?.trim()?.isNotEmpty() != true) {
            listChecks.add(false)
            binding.bodyText.error = "Введите текст новости"
        }

        if (imageUri == Uri.EMPTY) {
            listChecks.add(false)
            Toast.makeText(
                requireContext(),
                "Укажите картинку заголовка новости",
                Toast.LENGTH_LONG
            ).show()
        }

        return !listChecks.contains(false)
    }

    private fun validate() {
        binding.titleText.addTextChangedListener {
            binding.titleContainer.error = null
//            if (it != null)
//                viewModel.setName(it.trim().toString())
        }

        binding.bodyText.addTextChangedListener {
            binding.bodyContainer.error = null
//            if (it != null)
//                viewModel.setSurname(it.trim().toString())
        }
    }
}