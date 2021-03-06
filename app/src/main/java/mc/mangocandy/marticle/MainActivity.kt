package mc.mangocandy.marticle

import android.content.Intent
import android.media.Image
import android.os.Bundle

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.avos.avoscloud.AVUser
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mm.sdk.modelmsg.WXTextObject
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.ydwj.mangotools.MView.MProgressDialog
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import mc.mangocandy.mango.http.MHttpListener
import mc.mangocandy.mango.http.MHttpUtils
import mc.mangocandy.marticle.ArticleList.Adapter_article_pager

import mc.mangocandy.marticle.Base.MBaseActivity
import mc.mangocandy.marticle.Beans.ArticleType
import mc.mangocandy.marticle.Beans.Urls
import mc.mangocandy.marticle.FavoriteArticle.Act_Favorite
import mc.mangocandy.marticle.User.Act_userinfo
import mc.mangocandy.marticle.User.LoginActivity
import mc.mangocandy.marticle.User.UserUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : MBaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        askArticleType()
    }

    fun initView(){
        setSupportActionBar(mtoolbar)
        fab.setOnClickListener { view ->
            startActivity(LoginActivity::class.java)
//            if (AVUser.getCurrentUser() == null)
//                startActivity(LoginActivity::class.java)
        }
        val toggle = ActionBarDrawerToggle(
                this, drawer, mtoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        initUserInfo()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    fun initUserInfo(){
        var headimage = navigationView.getHeaderView(0).findViewById(R.id.headImage) as CircleImageView
        headimage.setOnClickListener {
            if(isLogin()){
                startActivity(Act_userinfo::class.java)
            }else{
                startActivity(LoginActivity::class.java)
            }
        }
        var username = navigationView.getHeaderView(0).findViewById(R.id.username) as TextView
        if(isLogin()){
            Glide.with(context).load(UserUtils.getUserImage()).error(R.drawable.icon_default_head).animate(R.anim.circle_s2b).into(headimage)
            username.text = UserUtils.getUserName()
        }else{
            Glide.with(context).load(R.drawable.icon_default_head).animate(R.anim.circle_s2b).into(headimage)
            username.text = "暂未登录"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        when(id){
            R.id.refresh ->{
                MProgressDialog.show(context,"正在加载...")
                askArticleType()
            }
        }

        return true
    }

    fun shareApp(){

        var image : UMImage = UMImage(context,R.drawable.icon_logo)
        val displaylist = arrayOf(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
        ShareAction(this)
                .setDisplayList(*displaylist)
                .withText("呵呵").withTitle("title")
                .withTargetUrl("")
                .withMedia(image)
                .setListenerList(umShareListener)
                .open()

    }

    var umShareListener = object : UMShareListener {
        override fun onResult(platform: SHARE_MEDIA) {
            Toast.makeText(context, platform.toString()+ " 分享成功啦", Toast.LENGTH_SHORT).show()
        }

        override fun onError(platform: SHARE_MEDIA, t: Throwable) {
            Toast.makeText(context, platform.toString()+ " 分享失败啦", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel(platform: SHARE_MEDIA) {
            Toast.makeText(context, platform.toString() + " 分享取消了", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        when(id){
            R.id.favorite ->{
                startActivity(Act_Favorite::class.java)
            }
            R.id.nav_share ->{
                shareApp()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    /***
     * Let's go
     */
    fun askArticleType(){
        var httpUtils = MHttpUtils(Urls.aTypeURL,object : MHttpListener(){
            override fun onFailure(message: String) {
                toast(R.string.net_error)
            }

            override fun onFinish() {
                MProgressDialog.dismiss()
            }

            override fun onSuccess(result: String) {
                var jsonObject = JSONObject(result)
                var reCode = jsonObject.getInt(Urls.SHOWAPI_RES_CODE)
                when(reCode){
                    0 ->{
                        getArticleType(jsonObject.getJSONObject(Urls.SHOWAPI_RES_BODY).getJSONArray(Urls.SHOWAPI_TYPELIST))
                    }
                    -1000 ->{
                        toast(R.string.system_maintenance)
                    }
                    else ->{
                        toast(R.string.net_error)
                    }
                }
            }
        })

        httpUtils.putRequestParams(Urls.SHOWAPI_APPID,Urls.showapi_appid)
        httpUtils.putRequestParams(Urls.SHOWAPI_SIGN,Urls.showapi_sign)
        httpUtils.putRequestParams(Urls.SHOWAPI_TIMESTAMP,Urls.showapi_timestamp)
        httpUtils.start()
    }

    fun getArticleType(jsonArray: JSONArray){
        var types = Gson().fromJson<List<ArticleType>>(jsonArray.toString(),object : TypeToken<List<ArticleType>>(){}.type)
        if(types.size<1){return}
        viewpager.adapter = Adapter_article_pager(supportFragmentManager,types)
        viewpager.offscreenPageLimit = 4
        tablayout.setupWithViewPager(viewpager)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
