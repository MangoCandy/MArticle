package mc.mangocandy.marticle.Base

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import android.widget.Toast
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import mc.mangocandy.MToast.MToast
import mc.mangocandy.marticle.R

open class MBaseActivity : AppCompatActivity() {
    open var toolbar: Toolbar? = null
    open var context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setTheme(R.style.blue)
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
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { onBackPressed() }
        toolbar!!.title = getString(titletext)
    }
    fun initToolbar(titletext : String){
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { onBackPressed() }
        toolbar!!.title = titletext
    }

    fun isLogin() : Boolean{
        return AVUser.getCurrentUser() != null
    }
}
