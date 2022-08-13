package com.mehyo.postsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mehyo.postsapp.databinding.FragmentCreatePostBinding

class CreatePostFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!
    private val args: CreatePostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            ivClose.setOnClickListener {
                this@CreatePostFragment.dismiss()
            }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}