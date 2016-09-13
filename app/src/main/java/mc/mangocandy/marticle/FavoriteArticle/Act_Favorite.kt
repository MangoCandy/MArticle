package mc.mangocandy.marticle.FavoriteArticle

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback
import kotlinx.android.synthetic.main.activity_favorite.*
import mc.mangocandy.mango.MRefreshLayout
import mc.mangocandy.marticle.Base.MBaseActivity
import mc.mangocandy.marticle.Beans.Favorite
import mc.mangocandy.marticle.Beans.Table
import mc.mangocandy.marticle.R
import mc.mangocandy.marticle.User.LoginActivity
import mc.mangocandy.marticle.User.UserUtils
import java.util.*

class Act_Favorite : MBaseActivity() {
    var REQUEST_LOGIN = -1
    var favorites = ArrayList<Favorite>()
    var adapter = Adapter_favorite_list(favorites)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        initToolbar(R.string.favorite)
//        needLogin(REQUEST_LOGIN)
        initView()
    }

    fun initView(){
        listview.layoutManager = LinearLayoutManager(this)
        listview.adapter = adapter

        refreshLayout.MsetCanLoad(true)
        refreshLayout.MsetOnRefreshListener(object : MRefreshLayout.OnRefreshListener(){
            override fun onRefresh() {
                favorites.clear()
                adapter.notifyDataSetChanged()
                askFavorite()
            }

            override fun onLoadMore() {
                askFavorite()
            }
        })
        refreshLayout.startRefresh()
    }

    fun askFavorite(){
        if(!isLogin()){
            if(!isLogin())
                startActivityForResult(LoginActivity::class.java,REQUEST_LOGIN)
            finish()
            return
        }
        AVQuery<Favorite>(Table.FAVORITE)
                .limit(20)
                .skip(favorites.size)
                .whereEqualTo(Favorite.USERID,UserUtils.getUserID())
                .findInBackground(object : FindCallback<Favorite>(){
                    override fun done(favorites: MutableList<Favorite>?, ex: AVException?) {
                        refreshLayout.MfinishRefreshOrLoadMore()
                        if(ex == null){
                            getFavorites(favorites!!)
                        }else{
                            toast("数据请求失败")
                        }
                    }
                })

    }

    fun getFavorites(favs : MutableList<Favorite>){
        for(fav in favs){
            favorites.add(fav)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            initView()
        }
    }
}
