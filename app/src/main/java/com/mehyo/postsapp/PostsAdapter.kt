package com.mehyo.postsapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.mehyo.postsapp.databinding.PostRowItemBinding

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var list = listOf<Post>()
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

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Post>) {
        this.list = list
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

    inner class PostsViewHolder(private val binding: PostRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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