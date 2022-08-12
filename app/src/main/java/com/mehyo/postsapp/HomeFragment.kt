package com.mehyo.postsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        initViews()
        initListeners()
    }

    private fun initViews() {
        postsAdapter = PostsAdapter()
        with(binding) {
            rvPosts.adapter = postsAdapter
        }
    }

    private fun initListeners() {
        with(binding) {

            postsAdapter.setOnPostItemClickListener { post ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPostDetailsFragment(
                        post
                    )
                )
            }
            postsAdapter.setOnPostItemDeleteListener { post ->
                createAlertDialog(
                    getString(R.string.delete_post_label),
                    getString(R.string.delete_post_message),
                    post
                )
            }
            postsAdapter.setOnPostItemEditListener { post ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToCreatePostFragment(
                        Constants.EDIT,
                        post
                    )
                )
            }
            fabAdd.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreatePostFragment())
            }
        }
    }

    private fun createAlertDialog(title: String, message: String, post: Post) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("DELETE") { dialog, _ ->
                Toast.makeText(requireContext(), "${post.title} is deleted", Toast.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}