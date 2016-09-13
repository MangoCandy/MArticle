package mc.mangocandy.marticle.Base

import android.app.Application
import com.avos.avoscloud.AVOSCloud
import com.avos.avoscloud.AVObject
import com.tencent.smtt.sdk.QbSdk
import com.umeng.socialize.PlatformConfig
import mc.mangocandy.marticle.Beans.Favorite
import mc.mangocandy.marticle.Beans.WeArticle
import org.xutils.x

/**
 * Created by Administrator on 2016/7/22.
 */
class MApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx75297ed685ae62c0", "92591a54597bac3839e17463df391e8d")

        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.preInit(this)
        x.Ext.init(this)//初始化xutils3
        // 初始化参数依次为 this, AppId, AppKey
        AVObject.registerSubclass(Favorite::class.java)
        AVOSCloud.initialize(this,"6F7GcAwlIUbAHgpPT0Twet1x-gzGzoHsz","nu0yFSuTWjxfdnHRcCUUKJqU")
    }
}