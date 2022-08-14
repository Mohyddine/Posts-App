package com.mehyo.postsapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.mehyo.postsapp.databinding.PostRowItemBinding
import com.mehyo.postsapp.model.Post

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var list = arrayListOf<Post>()
    private var onPostItemClickListener: ((Post) -> Unit)? = null
    private var onPostItemDeleteListener: ((Post) -> Unit)? = null
    private var onPostItemEditListener: ((Post) -> Unit)? = null

    private val viewBinderHelper = ViewBinderHelper()

    /**
     * function to implement item ClickListener
     * to get the clicked post object.
     */
    fun setOnPostItemClickListener(onPostItemClickListener: (Post) -> Unit): PostsAdapter {
        this.onPostItemClickListener = onPostItemClickListener
        return this
    }

    /**
     * function to implement item ClickListener
     * for deleting the clicked post.
     */
    fun setOnPostItemDeleteListener(onPostItemDeleteListener: (Post) -> Unit): PostsAdapter {
        this.onPostItemDeleteListener = onPostItemDeleteListener
        return this
    }

    /**
     * function to implement item ClickListener
     * for editing the clicked post.
     */
    fun setOnPostItemEditListener(onPostItemEditListener: (Post) -> Unit): PostsAdapter {
        this.onPostItemEditListener = onPostItemEditListener
        return this
    }

    /**
     * function for adding
     * all the posts to the list
     * then updating it.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Post>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * function for adding
     * a post to the list
     * then updating it.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun addPost(post: Post) {
        this.list.add(post)
        notifyDataSetChanged()
    }

    /**
     * function for removing
     * a post from the list
     * then updating it.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun removePost(post: Post) {
        this.list.remove(post)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostsViewHolder(
        PostRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) =
        holder.bind(list[position], viewBinderHelper)

    override fun getItemCount() = list.size

    /**
     * ViewHolder created for the Recyclerview Adapter and to
     * set the data in each row in the UI,
     * implement ClickListener.
     */
    inner class PostsViewHolder(private val binding: PostRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /**
         * Set the data in each row in the UI,
         * implement ClickListener.
         */
        fun bind(post: Post, viewBinderHelper: ViewBinderHelper) {
            with(binding) {
                tvTitle.text = post.title
                tvSubTitle.text = post.body
                main.setOnClickListener {
                    onPostItemClickListener?.invoke(post)
                }
                ivEdit.setOnClickListener {
                    onPostItemEditListener?.invoke(post)
                }
                ivDelete.setOnClickListener {
                    onPostItemDeleteListener?.invoke(post)
                }
                viewBinderHelper.setOpenOnlyOne(true)
                viewBinderHelper.bind(swipelayout, post.title)
                viewBinderHelper.closeLayout(post.title)
            }
        }
    }
}