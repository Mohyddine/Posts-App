package com.mehyo.postsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mehyo.postsapp.R
import com.mehyo.postsapp.databinding.FragmentHomeBinding
import com.mehyo.postsapp.model.Post
import com.mehyo.postsapp.network.ResourceState
import com.mehyo.postsapp.ui.adapter.PostsAdapter
import com.mehyo.postsapp.ui.viewmodel.HomeViewModel
import com.mehyo.postsapp.util.Constants
import com.mehyo.postsapp.util.gone
import com.mehyo.postsapp.util.visible
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var postsAdapter: PostsAdapter
    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getPostsAsync()
        initObservables()
        initViews()
        initListeners()
    }

    /**
     * function to initialise the Observables.
     * here we observe on: postsResultLiveData to get the list of posts from the server,
     * oldPostLiveData to get the old post from the bottom sheet,
     * addedPostLiveData to get the added post from the server,
     * updatedPostLiveData to get the edited post from the server.
     */
    private fun initObservables() {
        homeViewModel.postsResultLiveData.observe(viewLifecycleOwner) { result ->
            when (result.state) {
                is ResourceState.LOADING -> {
                    binding.apply {
                        progressBar.visible()
                        fabAdd.gone()
                        tvError.gone()
                        btnRefresh.gone()
                    }
                }

                is ResourceState.SUCCESS -> {
                    result.data?.let { listPostsData ->
                        binding.apply {
                            fabAdd.visible()
                            progressBar.gone()
                            tvError.gone()
                            btnRefresh.gone()
                        }
                        initList(listPostsData)
                    }
                }

                is ResourceState.ERROR -> {
                    binding.apply {
                        progressBar.gone()
                        fabAdd.gone()
                        tvError.visible()
                        btnRefresh.visible()
                    }
                    initList(emptyList())
                }
            }
        }

        homeViewModel.oldPostLiveData.observe(viewLifecycleOwner) { data ->
            data?.let { oldPost ->
                postsAdapter.removePost(post = oldPost)
            }
        }

        homeViewModel.addedPostLiveData.observe(viewLifecycleOwner) { post ->
            post?.let { result ->
                when (result.state) {
                    is ResourceState.SUCCESS -> {
                        result.data?.let { newPost ->
                            postsAdapter.addPost(post = newPost)
                        }
                    }

                    is ResourceState.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.general_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {}
                }
            }
        }

        homeViewModel.updatedPostLiveData.observe(viewLifecycleOwner) { result ->
            when (result.state) {
                is ResourceState.SUCCESS -> {
                    result.data?.let { newPost ->
                        postsAdapter.addPost(post = newPost)
                    }
                }
                is ResourceState.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.general_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }
        }
    }

    /**
     * function to initialise the list.
     */
    private fun initList(list: List<Post>) {
        postsAdapter.setData(list)
    }

    /**
     * function to initialise the views.
     */
    private fun initViews() {
        postsAdapter = PostsAdapter()
        with(binding) {
            rvPosts.adapter = postsAdapter
        }
    }

    /**
     * function to initialise Listeners.
     */
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
                if (post.id!! >= 100) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.cannot_edit),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToCreatePostFragment(
                            Constants.EDIT,
                            post
                        )
                    )
                }
            }
            fabAdd.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCreatePostFragment())
            }
            btnRefresh.setOnClickListener {
                homeViewModel.getPostsAsync()
            }
        }
    }

    /**
     * function to create the alertDialog the delete action is clicked,
     * if the user clicked DELETE deletePostAsync will be
     * called to delete the post from the server
     * and finally removing the post from the list.
     */
    private fun createAlertDialog(title: String, message: String, post: Post) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("DELETE") { dialog, _ ->
                post.id?.let { homeViewModel.deletePostAsync(it) }
                postsAdapter.removePost(post)
                Toast.makeText(
                    requireContext(),
                    "Post number ${post.id} deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
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