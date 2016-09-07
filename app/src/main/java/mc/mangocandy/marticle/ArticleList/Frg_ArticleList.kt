package mc.mangocandy.marticle.ArticleList

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_article_list.*
import mc.mangocandy.mango.MRefreshLayout
import mc.mangocandy.mango.http.MHttpListener
import mc.mangocandy.mango.http.MHttpUtils
import mc.mangocandy.marticle.Base.MBaseFragment
import mc.mangocandy.marticle.Beans.ArticleType
import mc.mangocandy.marticle.Beans.Urls
import mc.mangocandy.marticle.Beans.WeArticle
import mc.mangocandy.marticle.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*

/**
 * Created by MangoCandy on 2016/9/6.
 */
class Frg_ArticleList : MBaseFragment(){
    var currentPage = 1
    var articleType : ArticleType ?= null
    var articleList = ArrayList<WeArticle>()
    var adapter : Adapter_article_list = Adapter_article_list(articleList)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        frgView = inflater!!.inflate(R.layout.fragment_article_list,null)
        articleType = arguments.getSerializable("type") as ArticleType
        return frgView!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        if(articleList.size == 0){refreshLayout.startRefresh()}
    }

    fun initView(){
        listview.layoutManager = LinearLayoutManager(context)
        listview.adapter = adapter
        refreshLayout.MsetCanLoad(true)
        refreshLayout.MsetOnRefreshListener(object : MRefreshLayout.OnRefreshListener(){
            override fun onRefresh() {
                currentPage == 1
                articleList.clear()
                askArticleList()
            }

            override fun onLoadMore() {
                super.onLoadMore()
                askArticleList()
            }
        })
    }

    fun askArticleList(){
        var httpUtils = MHttpUtils(Urls.aListUrl,object : MHttpListener(){
            override fun onFailure(message: String) {
                toast(R.string.net_error)
            }

            override fun onSuccess(result: String) {
                currentPage++
                var jsonObject = JSONObject(result)
                var reCode = jsonObject.getInt(Urls.SHOWAPI_RES_CODE)
                when(reCode){
                    0 ->{
                        getArticleList(
                                jsonObject
                                        .getJSONObject(Urls.SHOWAPI_RES_BODY)
                                        .getJSONObject("pagebean")
                                        .getJSONArray(Urls.SHOWAPI_CONTENTLIST))
                    }
                    -1000 ->{
                        toast(R.string.system_maintenance)
                    }
                    else ->{
                        toast(R.string.net_error)
                    }
                }
            }

            override fun onFinish() {
                if (refreshLayout != null){
                    refreshLayout.MfinishRefreshOrLoadMore()
                }
            }
        })
        httpUtils.putRequestParams(Urls.SHOWAPI_APPID,Urls.showapi_appid)
        httpUtils.putRequestParams(Urls.SHOWAPI_SIGN,Urls.showapi_sign)
        httpUtils.putRequestParams(Urls.SHOWAPI_TIMESTAMP,Urls.showapi_timestamp)
        httpUtils.putRequestParams("typeId",articleType!!.id)
        httpUtils.putRequestParams("page",currentPage.toString())
        httpUtils.start()
    }

    fun getArticleList(jsonArray: JSONArray){
        var list = Gson().fromJson<List<WeArticle>>(jsonArray.toString(),object : TypeToken<List<WeArticle>>(){}.type)
        for(article in list){
            articleList.add(article)
        }
        adapter.notifyDataSetChanged()
    }

    var handler  = object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when(msg!!.what){
                NOTIFY_LIST ->{
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
    var NOTIFY_LIST = 0
}