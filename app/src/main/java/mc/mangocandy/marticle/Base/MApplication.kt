package mc.mangocandy.marticle.Base

import android.app.Application
import com.avos.avoscloud.AVOSCloud
import com.tencent.smtt.sdk.QbSdk
import org.xutils.x

/**
 * Created by Administrator on 2016/7/22.
 */
class MApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.preInit(this)
        x.Ext.init(this)//初始化xutils3
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"6F7GcAwlIUbAHgpPT0Twet1x-gzGzoHsz","nu0yFSuTWjxfdnHRcCUUKJqU");
    }
}