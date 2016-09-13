package mc.mangocandy.marticle.Base

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.avos.avoscloud.*
import mc.mangocandy.MToast.MToast
import mc.mangocandy.marticle.R
import mc.mangocandy.marticle.User.LoginActivity

open class MBaseActivity : AppCompatActivity() {
    open var toolbar: Toolbar? = null
    open var context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setTheme(R.style.pink)

        init()//获取权限

        if(isLogin()){
            AVUser.getCurrentUser().fetchInBackground(object : GetCallback<AVObject>(){
                override fun done(p0: AVObject?, p1: AVException?) {
                    //更新用户数据
                }
            })
        }
    }

    fun init(){
        val mPermissionList = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS)
        ActivityCompat.requestPermissions(this, mPermissionList, 100)
    }

    fun toast(message : String){
        MToast.show(this,message)
    }
    fun toast(message : Int){
        MToast.show(this,getString(message))
    }

    fun startActivity(c : Class<*>){
        var i : Intent = Intent(this,c)
        startActivity(i)
    }
    fun startActivityForResult(c : Class<*>,requestCode:Int){
        var i : Intent = Intent(this,c)
        startActivityForResult(i,requestCode)
    }

    fun initToolbar(titletext : Int){
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar!!.title = getString(titletext)
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { onBackPressed() }
    }
    fun initToolbar(titletext : String){
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar!!.title = titletext
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { onBackPressed() }
    }

    fun isLogin() : Boolean{
        return AVUser.getCurrentUser() != null
    }

    fun needLogin(requestCode: Int){
        if(!isLogin())
            startActivityForResult(LoginActivity::class.java,requestCode)
    }
}
