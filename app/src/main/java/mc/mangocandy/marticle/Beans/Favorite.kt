package mc.mangocandy.marticle.Beans

import com.avos.avoscloud.AVClassName
import com.avos.avoscloud.AVObject

/**
 * Created by MangoCandy on 2016/9/8.
 */
@AVClassName("Favorite")
open class Favorite : AVObject{
    var contentImg = ""
    var title = ""
    var url = ""
    var userName = ""
    var read_num = 0
    var id = ""

    companion object{
        val CONTENTIMG = "contentImg"
        val TITLE = "title"
        val URL = "url"
        val USERNAME = "userName"
        val USERID = "userId"
        var ARTICLE_ID = "articleID"
        var KEY = "key"
    }

    constructor()

    fun setKey(key : String){
        put(KEY,key)
    }
    fun getKey() = getString(KEY)
    //图片
    fun setContentIMG(path : String){
        put(WeArticle.CONTENTIMG,path)
    }
    fun getContentIMG() : String = getString(WeArticle.CONTENTIMG)
    //标题
    fun setTITLE(title : String){
        put(WeArticle.TITLE,title)
    }
    fun getTITLE() : String = getString(WeArticle.TITLE)
    //url
    fun setURL(url : String){
        put(WeArticle.URL,url)
    }
    fun getURL() = getString(WeArticle.URL)
    //作者
    fun setUSERNAME(name : String){ put(WeArticle.USERNAME, name)}
    fun getUSERNAME() = getString(WeArticle.USERNAME)
    //文章ID
    fun setArticleID(id : String){put(WeArticle.ARTICLE_ID,id)}
    fun getArticleID() = getString(WeArticle.ARTICLE_ID)
    //用户ID
    fun setUserID(id : String){put(WeArticle.USERID,id)}
    fun getUserID() = getString(WeArticle.USERID)
}