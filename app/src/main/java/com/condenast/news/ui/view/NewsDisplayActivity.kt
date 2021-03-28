package com.condenast.news.ui.view

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.condenast.news.R
import com.condenast.news.data.api.ApiHelper
import com.condenast.news.data.model.Article
import com.condenast.news.data.model.CommentResponse
import com.condenast.news.data.model.LikeResponse
import com.condenast.news.ui.viewmodel.LikeCommentViewModel
import com.condenast.news.ui.viewmodel.ViewModelFactory
import com.condenast.news.util.Constants.EXTRA_ARTICLE
import com.condenast.news.util.Status
import kotlinx.android.synthetic.main.activity_news_display.*
import kotlinx.android.synthetic.main.content_news_display.*
import retrofit2.Response

class NewsDisplayActivity : AppCompatActivity() {

    private val TAG:String = "NewsDisplayActivity"
    private val viewModel: LikeCommentViewModel by viewModels { ViewModelFactory(ApiHelper) }
    private lateinit var article: Article
    private lateinit var requestUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_display)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        article = intent.getParcelableExtra(EXTRA_ARTICLE)!!
        val url = article.url.toString()
        requestUrl = url.replace("/","-").substring(8)
        setupUI()
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.getLikes(requestUrl)
        viewModel.likes.observe(this, Observer {resources->
            resources.apply {
                when(status){
                    Status.SUCCESS-> {
                        var likeResponse = data.let { it as Response<LikeResponse>}
                        tv_likes.text = likeResponse.body()?.likes.toString()
                    }
                    Status.ERROR ->{
                        Log.d(TAG ,"error :$message")
                    }
                }
            }
        })

        viewModel.getComments(requestUrl)
        viewModel.comments.observe(this, Observer {resources->
            resources.apply {
                when(status){
                    Status.SUCCESS->{
                        var commentResponse = data.let { it as Response<CommentResponse>}
                        tv_comment.text = commentResponse.body()?.comments.toString()
                    }
                    Status.ERROR->{
                        Log.d(TAG ,"error :$message")
                    }
                }
            }
        })
    }

    private fun setupUI() {
        tv_title.text = article.title
        tv_description.text = article.description
        tv_content.text = article.content

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_content.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        Glide.with(img_news_thumb.context)
            .load(article.urlToImage).placeholder(R.drawable.ic_noimg)
            .error(R.drawable.news_img)
            .fallback(R.drawable.ic_noimg)
            .into(img_news_thumb)
    }
}