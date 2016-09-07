package mc.mangocandy.marticle.User


import android.os.Bundle

import mc.mangocandy.marticle.Base.MBaseActivity
import mc.mangocandy.marticle.Beans.Zhengze
import mc.mangocandy.marticle.R

import android.Manifest.permission.READ_CONTACTS
import android.graphics.Color
import android.util.Log
import com.avos.avoscloud.*
import com.bumptech.glide.Glide
import com.ydwj.mangotools.MView.MProgressDialog
import kotlinx.android.synthetic.main.activity_login.*
import mc.mangocandy.marticle.Base.TSActivity

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : TSActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initToolbar(R.string.title_activity_login)
        toolbar!!.setBackgroundColor(Color.TRANSPARENT)
        getImage()
        initView()
    }

    fun initView(){
        postButton.setOnClickListener { post() }
        mainText.setBackgroundColor(resources.getColor(R.color.blue))
    }


    var errorText = ""
    fun post(){
        if(checkUsername()){
            inputUsernameLayout.isErrorEnabled = false
        }else{
            inputUsernameLayout.error = errorText
            return
        }
        if(checkPassword()){
            inputPassWordLayout.isErrorEnabled = false
        }else{
            inputPassWordLayout.error = errorText
            return
        }
        login()

    }

    private fun login(){
        MProgressDialog.show(this,"请稍候...")
        AVUser.logInInBackground(username.text.toString(),
                password.text.toString(),object : LogInCallback<AVUser>(){
            override fun done(user: AVUser?, ex: AVException?) {
                if(ex !=null){
                    Log.e("asd",ex!!.message)
                    if(ex!!.code == 211) {//未注册
                        register()
                    }
                }
                else{
                    toast("欢迎回来,"+user!!.username)
                    MProgressDialog.dismiss()
                    finish()
                }
            }
        })
    }

    private fun register(){
        var user = AVUser()
        user.username = username.text.toString()
        user.setPassword(password.text.toString())
        user.signUpInBackground(object : SignUpCallback(){
            override fun done(ex: AVException?) {
                if(ex != null){
                    Log.e("asd",ex!!.message)
                }
                toast("注册成功,"+user.username)
                MProgressDialog.dismiss()
                finish()
            }
        })
    }

    fun checkUsername():Boolean{
        if(username.text.toString().length>10 ||username.text.toString().length <3){
            errorText = "用户名长度不得小于3位或大于10位"
            return false
        }
        return true
    }
    fun checkPassword():Boolean{
        if(password.text.toString().length<6){
            errorText = "密码长度不得少于六位"
            return false
        }
            return true
    }

    fun getImage() {//
        Glide.with(this).load("http://runningcheese.com/infinity.php").skipMemoryCache(true).animate(R.anim.circle_s2b).into(mainBackgroud)
//        val query : AVQuery<AVObject> = AVQuery<AVObject>("mangoImage")
//        query.findInBackground(object : FindCallback<AVObject>(){
//            override fun done(list: MutableList<AVObject>?, p1: AVException?) {
//                if (list!!.size == 0) {
//                    return
//                }
//                var obj : AVObject = list!![0]
//                val path: String
//                if (obj.getAVFile<AVFile>("image") != null) {
//                    path = obj.getAVFile<AVFile>("image").url
////                    Glide.with(context).load(path).animate(R.anim.circle_s2b).into(mainBackgroud)
//                }
//            }
//        })
    }
}

