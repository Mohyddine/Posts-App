package com.mehyo.postsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mehyo.postsapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var postsAdapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsAdapter = PostsAdapter()
        with(binding) {

            rvPosts.adapter = postsAdapter
            postsAdapter.setOnPostItemClickListener { post ->
                Toast.makeText(
                    requireContext(),
                    "${post.title} sent to details screen",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_homeFragment_to_postDetailsFragment)
            }
            postsAdapter.setOnPostItemDeleteListener { post ->
                Toast.makeText(requireContext(), "${post.title} is deleted", Toast.LENGTH_SHORT)
                    .show()
            }
            postsAdapter.setOnPostItemEditListener { post ->
                Toast.makeText(requireContext(), "${post.title} is Edited", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_homeFragment_to_createPostFragment)
            }
            fabAdd.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_createPostFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}