package mc.mangocandy.mango.http

/**
 * Created by MangoCandy on 2016/9/6.
 */
abstract class MHttpListener{
    abstract fun onSuccess(result : String)
    abstract fun onFailure(message : String)
    abstract fun onFinish()
}