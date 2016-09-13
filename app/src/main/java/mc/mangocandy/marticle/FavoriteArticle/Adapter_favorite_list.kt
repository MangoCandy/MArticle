package mc.mangocandy.marticle.FavoriteArticle

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
import mc.mangocandy.marticle.Beans.Favorite
import mc.mangocandy.marticle.Beans.WeArticle
import mc.mangocandy.marticle.R


/**
 * Created by MangoCandy on 2016/9/6.
 */
class Adapter_favorite_list : RecyclerView.Adapter<Adapter_favorite_list.MViewHolder> {
    override fun onBindViewHolder(holder: MViewHolder?, position: Int) {
        var favorite = favorites[position]
        holder!!.title.text = favorite.getTITLE()
        Glide.with(context).load(favorite.getContentIMG()).into(holder.image)
        holder.username.text = favorite.getUSERNAME()
        holder.readCount.visibility = View.GONE
        holder.itemView.setOnClickListener {
            var intent = Intent(context,MWebActivity::class.java)
            intent.putExtra(MWebActivity.URL,favorite.getURL())
            intent.putExtra(MWebActivity.TITLE,favorite.getTITLE())
            intent.putExtra(MWebActivity.ARTICLE_ID,favorite.getArticleID())
            intent.putExtra(MWebActivity.CONTENT_IMG,favorite.getContentIMG())
            intent.putExtra(MWebActivity.POST_USER_NAME,favorite.getUSERNAME())
            intent.putExtra(MWebActivity.ARTICLE_ID,favorite.getArticleID())
            context!!.startActivity(intent)
        }
    }

    var favorites :List<Favorite>
    constructor(favorites : List<Favorite>){
        this.favorites = favorites
    }
    var context : Context ?= null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MViewHolder? {
        context = parent!!.context
        return MViewHolder(LayoutInflater.from(context).inflate(R.layout.single_article_list,parent,false))
    }


    override fun getItemCount(): Int {
        return favorites.size
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
            readCount = itemView.findViewById(R.id.counttext) as TextView
        }
    }
}