package mc.mangocandy.marticle.Utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by MangoCandy on 2016/9/6.
 */
class TimeUtils{
    fun getTime(format : String) : String{
        var sdf = SimpleDateFormat(format)
        return sdf.format(Date())
    }
}