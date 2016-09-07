package com.ydwj.ailejia.Base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.avos.avoscloud.*
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.*
import com.ydwj.ailejia.View.AskIf
import kotlinx.android.synthetic.main.activity_mweb.*
import mc.mangocandy.marticle.Base.MBaseActivity
import mc.mangocandy.marticle.Beans.Table
import mc.mangocandy.marticle.Beans.WeArticle
import mc.mangocandy.marticle.R
import mc.mangocandy.marticle.User.LoginActivity


/**
    by-MangoCandy
    用来加载网页的Activity
    intent.putExtra(MWebActivity.URL,url)
 */
class MWebActivity : MBaseActivity(){


    companion object{
        val URL = "url"  //网页地址
        val TITLE = "title" //toolbar显示的文字
        val MODULE_ID = "moduleid" //通过点击模块按钮点进来时需要传递的值
        val ARTICLE = "article"//文章对象
    }

    var url : String = ""
    var title : String = ""
    var module_id : String = ""
    var article : WeArticle ?= null
    var progressBar : ProgressBar ?= null
    var artObject : AVObject ?= null //文章收藏储存对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QbSdk.allowThirdPartyAppDownload(true)
        setContentView(R.layout.activity_mweb)
        init()

        url = if(intent.extras.getString(URL) == null) "" else intent.extras.getString(URL)
        Log.e("asd",(intent.extras.getString(URL)).toString()+"asd")
        module_id = if(intent.extras.getString(MODULE_ID) == null) "" else intent.extras.getString(MODULE_ID)
        title =
                if(intent.extras.getString(TITLE) != null) intent.extras.getString(TITLE)
                else resources.getString(R.string.show_article)

        article = intent.extras.getSerializable(ARTICLE) as WeArticle?//获取传入的文章对象

        initView()
        checkFovorite(false)
        initData()
    }
    fun init(){

    }
    fun initView(){
        initWebView()
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar!!.setNavigationIcon(R.drawable.icon_close_light)
        toolbar!!.title = title
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { finish() }
        progressBar = findViewById(R.id.progressBar) as ProgressBar

        //bottom
        favorite.setOnClickListener { addFavorite() }
    }

    fun checkFovorite(delete : Boolean){
        favorite.isSelected = false
        AVQuery<AVObject>(Table.FAVORITE).whereEqualTo(WeArticle.URL,article!!.url).findInBackground(object : FindCallback<AVObject>(){
            override fun done(ao: MutableList<AVObject>?, ex: AVException?) {
                if(ex == null && ao!!.size>0){
                    favorite.isSelected = true
                    artObject = ao[0]
                    Log.e("asd",ao.size.toString())
                    if(delete){
                        artObject!!.deleteInBackground(object : DeleteCallback(){
                            override fun done(p0: AVException?) {
                                if(p0 == null){
                                    toast("已取消收藏")
                                    favorite.isSelected = false
                                }else{
                                    toast("取消收藏失败")
                                }
                            }
                        })
                    }
                }else{
                    favorite.isSelected = false
                }
            }
        })
    }

    fun addFavorite(){
        if(!isLogin()){
            startActivity(LoginActivity::class.java)
            return
        }
        if(favorite.isSelected){
            checkFovorite(true)

        }else{
            var ao = AVObject(Table.FAVORITE)
            ao.put(WeArticle.URL,article!!.url)
            ao.put(WeArticle.TITLE,article!!.title)
            ao.put(WeArticle.CONTENTIMG,article!!.contentImg)
            ao.put(WeArticle.USERNAME,article!!.userName)
            ao.put(WeArticle.USERID,AVUser.getCurrentUser().objectId)
            ao.saveInBackground(object : SaveCallback(){
                override fun done(p0: AVException?) {
                    if(p0 == null){
                        toast("收藏成功")
                        favorite.isSelected = true

                    }
                    else
                    {
                        toast("收藏失败,请重试")
                        Log.e("asd",p0.message)
                    }
                }
            })
        }
    }

    val FILECHOOSER_RESULTCODE = 1
    val FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2
    /**
        by - MangoCandy
        初始化web view
     */
    var webchromeClient : WebChromeClient ?= null

    fun initWebView(){
        webchromeClient = object  : WebChromeClient(){
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                toast(message!!)
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                var ask =object: AskIf(context,message!!){
                    override fun onCancel() {
                        result!!.cancel()
                    }

                    override fun onDone() {
                        result!!.confirm()
                    }
                }
                ask.cancelable = false
                ask.show()
                return true
            }
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                progressBar!!.progress = newProgress
                progressBar!!.visibility = if(newProgress >=100) View.GONE else View.VISIBLE
            }

            override fun openFileChooser(uploadMsg: ValueCallback<Uri?>?, p1: String?, p2: String?) {
                callback4droid4 = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE)
            }

            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri?>>?, fileChooserParams: FileChooserParams?): Boolean {
                callback4droid5 = filePathCallback
                val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                contentSelectionIntent.type = "image/*"

                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")

                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5)
                return true
            }
        }
        webview!!.view.setOnLongClickListener { v ->
//            var htr =  HitTestResult()
            //获取所点击的内容
            val result = webview!!.hitTestResult
            if (result != null) {
                val type = result.type
                if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    var imgurl = result.extra

                    true
                }
                false
            }
            false
        }

        webview!!.setWebViewClient(object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.e("qwer",url)
                if (url!!.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:")) {//拨号
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    return true
                }
                if(url.startsWith("baidumap://map/?src=")){//防止打开百度APP
                    return true
                }
                if(url.startsWith("baidumap://map/direction?")&&url.contains("webapp.default.all")){//非点击导航按钮
                    return true
                }
                view!!.loadUrl(url)
                return true
            }
        })
        Log.e("web",(webview.x5WebViewExtension == null).toString())
        webview!!.setWebChromeClient(webchromeClient)
        var websetting : WebSettings = webview!!.settings
        websetting.loadsImagesAutomatically = true
        websetting.allowFileAccess = true
        websetting.allowContentAccess = true
        websetting.lightTouchEnabled = true
        websetting.setAllowFileAccessFromFileURLs(true)
        websetting.setAllowUniversalAccessFromFileURLs(true)
//        websetting.setUserAgent(websetting.userAgentString+"MicroMessenger" )
        websetting.blockNetworkImage = false
        websetting.javaScriptCanOpenWindowsAutomatically = true
        websetting.javaScriptEnabled = true
        websetting.defaultTextEncodingName = "UTF-8"
        websetting.domStorageEnabled = true
        websetting.builtInZoomControls = false
//        websetting.setSupportZoom(false)
        websetting.useWideViewPort = true
        websetting.loadWithOverviewMode = true
        websetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        websetting.setGeolocationEnabled(true)
        websetting.databaseEnabled = true
        val dir = this.applicationContext.getDir("database", Context.MODE_PRIVATE).path
        websetting.setGeolocationDatabasePath(dir)

    }
    var callback4droid5 : ValueCallback<Array<Uri?>> ? = null
    var callback4droid4 : ValueCallback<Uri?> ? = null
    fun initData(){
        if(!url .equals(""))
            webview!!.loadUrl(url)
    }

    override fun onBackPressed() {
        if(webview!!.canGoBack()){
            webview!!.goBack()
        }else{super.onBackPressed()}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//
        if (requestCode === FILECHOOSER_RESULTCODE) {
            if (null == callback4droid4)
                return
            val result = if (data == null || resultCode !== RESULT_OK)
                null
            else
                data.data
            callback4droid4!!.onReceiveValue(result)
            callback4droid4 = null

        }

        if (requestCode === FILECHOOSER_RESULTCODE_FOR_ANDROID_5) {
            if (null == callback4droid5)
                return
            val result = if (intent == null || resultCode !== RESULT_OK) null else data!!.data
            if (result != null) {
                callback4droid5!!.onReceiveValue(arrayOf(result))
            } else {
                callback4droid5!!.onReceiveValue(arrayOf<Uri?>())
            }
            callback4droid5 = null
        }
    }

    override fun onResume() {
        super.onResume()
        webview!!.onResume()
    }
}