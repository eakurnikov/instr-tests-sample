package com.eakurnikov.instrsample.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.eakurnikov.instrsample.R
import com.eakurnikov.instrsample.common.Resource
import com.eakurnikov.instrsample.common.lazyUnsync
import com.eakurnikov.instrsample.data.dto.PostDto
import com.eakurnikov.instrsample.viewmodel.PostsViewModel

class PostsActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context): Unit =
            context.startActivity(Intent(context, PostsActivity::class.java))
    }

    private val refreshLayout: SwipeRefreshLayout by lazyUnsync {
        findViewById(R.id.layout_refresh_posts)
    }
    private val postsList: RecyclerView by lazyUnsync {
        findViewById(R.id.list_posts)
    }
    private val progressBar: ProgressBar by lazyUnsync {
        findViewById(R.id.progress_bar_posts)
    }
    private val errorLabel: TextView by lazyUnsync {
        findViewById(R.id.tv_posts_error)
    }
    private val viewModel: PostsViewModel by lazyUnsync {
        ViewModelProvider(this).get(PostsViewModel::class.java)
    }

    private val adapter = PostsAdapter()

    private val postsObserver = Observer { resource: Resource<List<PostDto>> ->
        when (resource) {
            is Resource.Success -> when {
                resource.data.isEmpty() -> showEmptyList()
                else -> showPosts(resource.data)
            }
            is Resource.Loading -> showLoading(false)
            is Resource.Error -> showError(resource.message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        initViews()
    }

    override fun onStart() {
        super.onStart()
        viewModel.apply {
            posts.observe(this@PostsActivity, postsObserver)
            onStart()
        }
    }

    private fun initViews() {
        refreshLayout.setOnRefreshListener {
            showLoading(true)
            viewModel.onRefresh()
        }

        postsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PostsActivity)
            adapter = this@PostsActivity.adapter
        }
    }

    private fun showLoading(isRefresh: Boolean) {
        if (isRefresh) {
            refreshLayout.isRefreshing = true
            progressBar.visibility = View.GONE
        } else if (!refreshLayout.isRefreshing) {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun showPosts(posts: List<PostDto>) {
        refreshLayout.isRefreshing = false
        progressBar.visibility = View.GONE
        postsList.visibility = View.VISIBLE
        errorLabel.visibility = View.GONE

        adapter.apply {
            data = posts
            notifyDataSetChanged()
        }
    }

    private fun showEmptyList() {
        refreshLayout.isRefreshing = false
        progressBar.visibility = View.GONE
        postsList.visibility = View.GONE
        errorLabel.visibility = View.VISIBLE
        errorLabel.text = getString(R.string.no_entries)
    }

    private fun showError(message: String?) {
        refreshLayout.isRefreshing = false
        progressBar.visibility = View.GONE
        postsList.visibility = View.GONE
        errorLabel.visibility = View.VISIBLE
        errorLabel.text = message ?: getString(R.string.error)
    }

    class PostsAdapter : RecyclerView.Adapter<PostViewHolder>() {
        var data: List<PostDto> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
            PostViewHolder.create(parent)

        override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int): Unit =
            viewHolder.bind(position, data[position])

        override fun getItemCount(): Int = data.size
    }

    class PostViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        companion object {
            fun create(parent: ViewGroup) = PostViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_post, parent, false)
            )
        }

        var item: LinearLayout = itemView.findViewById(R.id.item_post)
        var login: TextView = itemView.findViewById(R.id.tv_post_title)
        var password: TextView = itemView.findViewById(R.id.tv_post_body)

        fun bind(position: Int, post: PostDto) {
            item.setBackgroundColor(if (position % 2 == 0) Color.WHITE else Color.GRAY)
            login.text = post.title
            password.text = post.body
        }
    }
}
