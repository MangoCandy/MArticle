package mc.mangocandy.marticle.User

import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVUser

/**
 * Created by MangoCandy on 2016/9/7.
 */
class UserUtils{
    companion object{
        fun getUserImage() : String{
            AVUser.getCurrentUser().signUp()
            var file = AVUser.getCurrentUser().getAVFile<AVFile>("userimage")
            return if(file == null){""} else file.url
        }
        fun getUserName():String{
            if(AVUser.getCurrentUser() == null){
                return "暂未登录"
            }
            return AVUser.getCurrentUser().username
        }
    }
}