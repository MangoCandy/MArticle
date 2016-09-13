package mc.mangocandy.marticle.ArticleList

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ydwj.ailejia.Base.MWebActivity
import de.hdodenhof.circleimageview.CircleImageView
import mc.mangocandy.marticle.Beans.WeArticle
import mc.mangocandy.marticle.R


/**
 * Created by MangoCandy on 2016/9/6.
 */
class Adapter_article_list : RecyclerView.Adapter<Adapter_article_list.MViewHolder> {
    var articles :List<WeArticle>
    constructor(articles : List<WeArticle>){
        this.articles = articles
    }
    var context : Context ?= null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder {
        context = parent!!.context
        return MViewHolder(LayoutInflater.from(context).inflate(R.layout.single_article_list,parent,false))
    }

    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {
        var article = articles[position]
        holder!!.title.text = article.title
        Glide.with(context).load(article.contentImg).into(holder.image)
        holder.username.text = article.userName
        holder.readCount.text = article.read_num.toString()
        holder.itemView.setOnClickListener {
            var intent = Intent(context,MWebActivity::class.java)
            intent.putExtra(MWebActivity.URL,article.url)
            intent.putExtra(MWebActivity.TITLE,article.title)
            intent.putExtra(MWebActivity.ARTICLE,article)
            intent.putExtra(MWebActivity.CONTENT_IMG,article.contentImg)
            intent.putExtra(MWebActivity.POST_USER_NAME,article.userName)
            intent.putExtra(MWebActivity.ARTICLE_ID,article!!.id)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class MViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var image : CircleImageView
        var title : TextView
        var username : TextView
        var readCount : TextView
        init {
            image = itemView!!.findViewById(R.id.articleImage) as CircleImageView
            title = itemView.findViewById(R.id.title) as TextView
            username = itemView.findViewById(R.id.username) as TextView
            readCount = itemView.findViewById(R.id.readcount) as TextView
        }
    }
}