package com.mehyo.postsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mehyo.postsapp.databinding.FragmentCreatePostBinding
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

    private fun initListeners() {
        with(binding) {
            ivClose.setOnClickListener {
                this@CreatePostFragment.dismiss()
            }
            btnCreate.setOnClickListener {
                when (args.input) {
                    Constants.CREATE -> {
                        addPost()
                        this@CreatePostFragment.dismiss()
                    }
                    Constants.EDIT -> {
                        editPost()
                        this@CreatePostFragment.dismiss()
                    }
                }
            }
        }
    }

    private fun editPost() {
        val newPost = Post(
            userId = 1,
            title = binding.edtPostTitle.text.toString().trim(),
            body = binding.edtPostDesc.text.toString().trim()
        )
        args.post?.let { homeViewModel.editPost(it) }
        args.post?.id?.let { homeViewModel.editPostByIdAsync(newPost, it) }
    }

    private fun addPost() {
        val newPost = Post(
            userId = 1,
            title = binding.edtPostTitle.text.toString().trim(),
            body = binding.edtPostDesc.text.toString().trim()
        )
        homeViewModel.addPostAsync(newPost)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}