package com.condenast.news.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.condenast.news.R
import com.condenast.news.data.model.Article
import kotlinx.android.synthetic.main.news_item_layout.view.*

class NewsAdapter(private val articles: MutableList<Article>) : RecyclerView.Adapter<NewsAdapter.DataViewHolder>() {

    private var onItemClickListener : ((Article) -> Unit)? = null

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            article: Article,
            onItemClickListener: ((Article) -> Unit)?
        ){
            itemView.apply {
                tvTitle.text = article.title
                tvAuthor.text = article.author
                tvPublishedAt.text = article.publishedAt
                Glide.with(ivArticleImage.context)
                    .load(article.urlToImage).placeholder(R.drawable.ic_noimg)
                    .error(R.drawable.ic_error_image)
                    .fallback(R.drawable.ic_noimg)
                    .into(ivArticleImage)
                setOnClickListener { onItemClickListener?.let { it(article) } }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item_layout, parent, false))

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
       holder.bind(articles[position], onItemClickListener)
    }

    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    /**
     * @param articles
     * adding news items
     */
    fun addNews(articles: List<Article>){
        this.articles.apply {
            clear()
            addAll(articles)
        }
    }
}

