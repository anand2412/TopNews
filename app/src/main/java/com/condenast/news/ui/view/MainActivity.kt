package com.condenast.news.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.condenast.news.data.api.ApiHelper
import com.condenast.news.data.api.RetrofitBuilder
import com.condenast.news.data.model.NewsResponse
import com.condenast.news.ui.adapter.NewsAdapter
import com.condenast.news.ui.viewmodel.NewsViewModel
import com.condenast.news.ui.viewmodel.ViewModelFactory
import com.condenast.news.util.Status
import com.condenast.news.R
import com.condenast.news.util.Constants.EXTRA_CONTENT
import com.condenast.news.util.Constants.EXTRA_DESCRIPTION
import com.condenast.news.util.Constants.EXTRA_IMAGEURL
import com.condenast.news.util.Constants.EXTRA_TITLE
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val viewModel: NewsViewModel by viewModels { ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) }
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupObservers()
        setupClickListener()
    }

    private fun setupClickListener() {
        adapter.setOnClickListener {
            val intent = Intent(this, NewsDisplayActivity::class.java).apply {
                putExtra(EXTRA_IMAGEURL, it.urlToImage)
                putExtra(EXTRA_TITLE, it.title)
                putExtra(EXTRA_DESCRIPTION, it.description)
                putExtra(EXTRA_CONTENT, it.content)
            }
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        viewModel.getTopUSNews("us", "business")
        viewModel.topUSNews.observe(this, Observer { resource ->
            resource?.apply {
                when (status) {
                    Status.SUCCESS -> {
                        rv_news.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        data?.let { newsResponse -> displayNewsList(newsResponse as Response<NewsResponse>) }
                    }
                    Status.ERROR -> {
                        rv_news.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        rv_news.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun displayNewsList(response: Response<NewsResponse>) {
        adapter.apply {
            addNews(response.body()?.articles ?: emptyList())
            notifyDataSetChanged()
        }
    }

    private fun setupUI() {
        rv_news.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(arrayListOf())
        rv_news.addItemDecoration(
            DividerItemDecoration(
                rv_news.context,
                (rv_news.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv_news.adapter = adapter
    }
}