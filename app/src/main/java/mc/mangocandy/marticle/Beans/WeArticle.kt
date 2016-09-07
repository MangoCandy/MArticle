package mc.mangocandy.marticle.Beans

import java.io.Serializable

/**
 * Created by MangoCandy on 2016/9/6.
 */
class WeArticle : Serializable{
    var contentImg = ""
    var title = ""
    var url = ""
    var userName = ""
    var read_num = 0

    companion object{
        val CONTENTIMG = "contentImg"
        val TITLE = "title"
        val URL = "url"
        val USERNAME = "userName"
        val USERID = "userId"
    }
}