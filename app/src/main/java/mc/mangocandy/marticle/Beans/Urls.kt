package mc.mangocandy.marticle.Beans

import mc.mangocandy.marticle.Utils.TimeUtils

/**
 * Created by MangoCandy on 2016/9/6.
 */
class Urls{
    companion object{
        //易源所需参数
        val SHOWAPI_APPID = "showapi_appid"
        val SHOWAPI_SIGN = "showapi_sign"
        val SHOWAPI_TIMESTAMP = "showapi_timestamp"

        val SHOWAPI_RES_CODE = "showapi_res_code"//请求返回结果码
        val SHOWAPI_RES_BODY = "showapi_res_body" // 请求返回结果主体
        val SHOWAPI_TYPELIST = "typeList"//请求结果集
        var SHOWAPI_CONTENTLIST = "contentlist" //文章列表集

        var showapi_appid = "24147"
        var showapi_sign = "0fa3ced1c3a740608121bb84b78d6df0"
        var showapi_timestamp = TimeUtils().getTime("yyyyMMddHHmmss")

        //易源所需参数 截止
        val aTypeURL = "http://route.showapi.com/582-1"//热文类别请求地址
        val aListUrl = "http://route.showapi.com/582-2"//热文列表请求地址
    }
}