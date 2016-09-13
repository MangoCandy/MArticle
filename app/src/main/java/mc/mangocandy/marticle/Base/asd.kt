package mc.mangocandy.marticle.Base

import android.app.Activity
import android.content.Context

import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA

import android.support.design.R.id.image

/**
 * Created by MangoCandy on 2016/9/13.
 */

class asd {
    internal var context: Context? = null
    internal fun sha() {
        val displaylist = arrayOf(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN)
        ShareAction(context as Activity?).setDisplayList(*displaylist).withText("呵呵").withTitle("title").withTargetUrl("http://www.baidu.com").open()//                .withMedia( image )
        //                .setListenerList(umShareListener)
    }
}
