package com.condenast.news.ui.view

import android.content.Intent
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.condenast.news.R
import com.condenast.news.util.Constants.EXTRA_CONTENT
import com.condenast.news.util.Constants.EXTRA_DESCRIPTION
import com.condenast.news.util.Constants.EXTRA_IMAGEURL
import com.condenast.news.util.Constants.EXTRA_TITLE
import kotlinx.android.synthetic.main.activity_news_display.*
import kotlinx.android.synthetic.main.content_news_display.*
import kotlinx.android.synthetic.main.news_item_layout.view.*

class NewsDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_display)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tv_title.text = intent.getStringExtra(EXTRA_TITLE);
        tv_description.text = intent.getStringExtra(EXTRA_DESCRIPTION)
        tv_content.text = intent.getStringExtra(EXTRA_CONTENT)
        val imageUrl = intent.getStringExtra(EXTRA_IMAGEURL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_content.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        Glide.with(img_news_thumb.context)
            .load(imageUrl).placeholder(R.drawable.ic_noimg)
            .error(R.drawable.news_img)
            .fallback(R.drawable.ic_noimg)
            .into(img_news_thumb)
    }
}