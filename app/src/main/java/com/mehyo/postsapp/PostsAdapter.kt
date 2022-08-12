package com.mehyo.postsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.mehyo.postsapp.databinding.PostRowItemBinding

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var onPostItemClickListener: ((Post) -> Unit)? = null
    private var onPostItemDeleteListener: ((Post) -> Unit)? = null
    private var onPostItemEditListener: ((Post) -> Unit)? = null

    private val viewBinderHelper = ViewBinderHelper()

    fun setOnPostItemClickListener(onPostItemClickListener: (Post) -> Unit): PostsAdapter {
        this.onPostItemClickListener = onPostItemClickListener
        return this
    }

    fun setOnPostItemDeleteListener(onPostItemDeleteListener: (Post) -> Unit): PostsAdapter {
        this.onPostItemDeleteListener = onPostItemDeleteListener
        return this
    }

    fun setOnPostItemEditListener(onPostItemEditListener: (Post) -> Unit): PostsAdapter {
        this.onPostItemEditListener = onPostItemEditListener
        return this
    }

    private val list = listOf(
        Post("Hello 1 Hello World one", "Hello World one Hello 1 Hello World one"),
        Post("Hello 2 Hello World Two", "Hello World Two Hello 2 Hello World Two"),
        Post("Hello 3 Hello World Three", "Hello World Three Hello 3 Hello World Three"),
        Post("Hello 4 Hello World Four", "Hello World Four Hello 4 Hello World Four"),
        Post("Hello 5 Hello World Five", "Hello World Five Hello 5 Hello World Five")
    )

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

    inner class PostsViewHolder(private val binding: PostRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, viewBinderHelper: ViewBinderHelper) {
            with(binding) {
                tvTitle.text = post.title
                tvSubTitle.text = post.desc
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