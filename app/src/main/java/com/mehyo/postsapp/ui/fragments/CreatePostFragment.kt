package com.mehyo.postsapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mehyo.postsapp.R
import com.mehyo.postsapp.databinding.FragmentCreatePostBinding
import com.mehyo.postsapp.model.Post
import com.mehyo.postsapp.ui.viewmodel.HomeViewModel
import com.mehyo.postsapp.util.Constants
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreatePostFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!
    private val args: CreatePostFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    /**
     * function to initialise the views.
     */
    private fun initViews() {
        with(binding) {
            when (args.input) {
                Constants.CREATE -> {
                    tvLabelCreate.setText(R.string.create_a_new_post)
                }
                Constants.EDIT -> {
                    tvLabelCreate.setText(R.string.edit_post)
                    args.post?.let {
                        edtPostTitle.setText(it.title)
                        edtPostDesc.setText(it.body)
                    }
                }
                else -> {}
            }
        }
    }

    /**
     * function to initialise Listeners.
     */
    private fun initListeners() {
        with(binding) {
            ivClose.setOnClickListener {
                this@CreatePostFragment.dismiss()
            }
            btnCreate.setOnClickListener {
                when (args.input) {
                    Constants.CREATE -> {
                        addPost()
                    }
                    Constants.EDIT -> {
                        editPost()
                    }
                }
            }
        }
    }

    /**
     * function to do the editPost action
     * 1st we check if all the fields are not empty,
     * then we send the old post to the home screen,
     * then we create a new post using the fields input
     * and finally we call editPostByIdAsync
     * to edit the post on the server after that
     * the bottom sheet will be dismissed.
     */
    private fun editPost() {
        if (TextUtils.isEmpty(binding.edtPostTitle.text.toString().trim())
            or
            TextUtils.isEmpty(binding.edtPostDesc.text.toString().trim())
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.required_fields),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val newPost = Post(
                userId = 1,
                title = binding.edtPostTitle.text.toString().trim(),
                body = binding.edtPostDesc.text.toString().trim()
            )
            args.post?.let { homeViewModel.sendOldPost(it) }
            args.post?.id?.let { homeViewModel.editPostByIdAsync(newPost, it) }
            this@CreatePostFragment.dismiss()
        }
    }

    /**
     * function to do the addPost action
     * 1st we check if all the fields are not empty,
     * then we create a new post object using the fields input
     * and finally we call addPostAsync
     * to create the post on the server after that
     * the bottom sheet will be dismissed.
     */
    private fun addPost() {
        if (TextUtils.isEmpty(binding.edtPostTitle.text.toString().trim())
            or
            TextUtils.isEmpty(binding.edtPostDesc.text.toString().trim())
        ) {
            Toast.makeText(
                requireContext(),
                getString(R.string.required_fields),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val newPost = Post(
                userId = 1,
                title = binding.edtPostTitle.text.toString().trim(),
                body = binding.edtPostDesc.text.toString().trim()
            )
            homeViewModel.addPostAsync(newPost)
            this@CreatePostFragment.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}