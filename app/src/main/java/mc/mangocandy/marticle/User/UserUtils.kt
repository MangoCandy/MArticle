package mc.mangocandy.marticle.User

import android.content.Context
import android.util.Log
import com.avos.avoscloud.*
import com.ydwj.mangotools.MView.MProgressDialog
import mc.mangocandy.MToast.MToast

/**
 * Created by MangoCandy on 2016/9/7.
 */
abstract class UserUtils{
    companion object{
        fun getUserImage() : String{
            var file = AVUser.getCurrentUser().getString("userimage")
            return if(file == null){""} else file
        }
        fun getUserName():String{
            if(AVUser.getCurrentUser() == null){
                return "暂未登录"
            }
            return AVUser.getCurrentUser().username
        }
        fun getUserID() = AVUser.getCurrentUser().objectId

        var avfile : AVFile ?= null
    }


    fun changeHeadImage(context : Context , path : String){
        MProgressDialog.show(context,"请稍候...")
        avfile = AVFile.withAbsoluteLocalPath(UserUtils.getUserID().toString()+".png",path)
        avfile!!.saveInBackground(object : SaveCallback(){
            override fun done(p0: AVException?) {
                if(p0 == null){
                    modifyhead(context)
                }else{
                    MProgressDialog.dismiss()
                    MToast.show(context , "修改失败,请重新尝试")
                }
            }
        })
    }

    fun modifyhead(context : Context ){
        var user  = AVUser.getCurrentUser()
        user.put("userimage", avfile!!.url)
        AVUser.getCurrentUser().saveInBackground(object : SaveCallback(){
            override fun done(p0: AVException?) {
                MProgressDialog.dismiss()
                if(p0 == null){
                    changeSuccess()
                }else{
                    MToast.show(context , "修改失败,请重新尝试")
                }
            }
        })
    }
    open fun changeSuccess(){}
}