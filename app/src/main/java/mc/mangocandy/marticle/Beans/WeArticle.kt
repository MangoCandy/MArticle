package mc.mangocandy.marticle.Beans

import android.os.Parcel
import com.avos.avoscloud.AVClassName
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUtils
import java.io.Serializable

/**
 * Created by MangoCandy on 2016/9/6.
 */
open class WeArticle : Serializable {
    constructor()
    constructor(table : String){
        AVUtils.checkClassName(table)
    }
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
    }
}