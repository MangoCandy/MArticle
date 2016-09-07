package mc.mangocandy.mango.http

import android.util.Log
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import java.io.File

/**
 * Created by Administrator on 2016/7/21.
 */
class MHttpUtils(url:String,httpListener: MHttpListener)//利用xutils3 修改网络请求框架直接修改这里
{
    var httpListener : MHttpListener
    var requestParams:RequestParams
    init {
        this.httpListener = httpListener
        requestParams = RequestParams(url)
    }

    fun start(){
        x.http().post(requestParams,object :Callback.CommonCallback<String>{
            override fun onSuccess(result: String?) {
                httpListener.onSuccess(result!!)
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                httpListener.onFailure(ex!!.message!!)
                Log.e("asd",ex.toString())
            }

            override fun onFinished() {
                httpListener.onFinish()
            }

            override fun onCancelled(cex: Callback.CancelledException?) {

            }
        })
    }

    fun setRequestParams(params:Map<String,String>){
        requestParams!!.clearParams()
        for((key,value) in params){
            requestParams!!.addBodyParameter(key,value)
        }
    }

    fun putRequestParams(key:String,value:String){
        requestParams!!.addBodyParameter(key,value)
    }

    fun MsetMultipart(boolean: Boolean){
        requestParams!!.isMultipart = boolean
    }

    fun putBodyParameter(name : String,file : File){
        requestParams!!.addBodyParameter(name,file)
    }
}






