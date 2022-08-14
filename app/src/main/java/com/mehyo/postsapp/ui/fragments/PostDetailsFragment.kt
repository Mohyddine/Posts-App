package com.mehyo.postsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mehyo.postsapp.R
import com.mehyo.postsapp.databinding.FragmentPostDetailsBinding
import com.mehyo.postsapp.util.gone
import com.mehyo.postsapp.util.visible

class PostDetailsFragment : Fragment() {

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    private var moreSwitcher = true
    private val args: PostDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        with(binding) {
            args.post?.let {
                tvTitle.text = it.title
                tvDesc.text = it.body
            }
            tvDesc.post {
                when (tvDesc.lineCount) {
                    in 0..2 -> {
                        tvReadMore.gone()
                    }
                    else -> {
                        tvReadMore.visible()
                    }
                }
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            tvReadMore.setOnClickListener {
                if (moreSwitcher) {
                    tvDesc.maxLines = Integer.MAX_VALUE
                    moreSwitcher = false
                    tvReadMore.setText(R.string.hide)
                } else {
                    tvDesc.maxLines = 2
                    moreSwitcher = true
                    tvReadMore.setText(R.string.read_more)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}