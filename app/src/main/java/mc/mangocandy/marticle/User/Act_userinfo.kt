package mc.mangocandy.marticle.User

import android.content.Intent
import android.os.Bundle
import com.avos.avoscloud.*
import com.bumptech.glide.Glide
import com.yancy.imageselector.ImageConfig
import com.yancy.imageselector.ImageSelector
import com.yancy.imageselector.ImageSelectorActivity
import com.ydwj.ailejia.View.AskIf
import kotlinx.android.synthetic.main.activity_userinfo.*
import mc.mangocandy.marticle.Base.MBaseActivity

import mc.mangocandy.marticle.R
import mc.mangocandy.marticle.Utils.GlideLoader

class Act_userinfo : MBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)
        initToolbar(R.string.title_activity_act_userinfo)
        needLogin(0)
        initView()
        initData()
    }

    fun initView(){
        headImage.setOnClickListener {
            val imageConfig = ImageConfig.Builder(GlideLoader()).steepToolBarColor(context.resources.getColor(R.color.colorPrimaryDark)).titleBgColor(context.resources.getColor(R.color.colorPrimary)).titleSubmitTextColor(context.resources.getColor(R.color.white)).titleTextColor(context.resources.getColor(R.color.white)).crop().singleSelect()// 开启多选   （默认为多选）
                    .showCamera()// 开启拍照功能 （默认关闭）
                    .filePath("/zhsq/Pictures")// 已选择的图片路径
                    //                        .pathList(path)
                    // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                    .build()
            ImageSelector.open(this, imageConfig)
        }
        logoutButton.setOnClickListener {
            object : AskIf(this,"是否退出登录"){
                override fun onCancel() {

                }

                override fun onDone() {
                    AVUser.logOut()
                    finish()
                }
            }.show()
        }
    }

    fun initData(){
        if(isLogin()){
            Glide.with(this).load(UserUtils.getUserImage()).error(R.drawable.icon_default_head).into(headImage)
            username.setText(UserUtils.getUserName())
        }else
            return
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            val pathList = data!!.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT)
            var path = ""
            for (p in pathList) {
                path = p
            }
            object : UserUtils(){
                override fun changeSuccess() {
                    initData()
                }
            }.changeHeadImage(this,path)
        }
    }

}
