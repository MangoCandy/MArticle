package mc.mangocandy.marticle.Base

import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * Created by MangoCandy on 2016/9/6.
 */
open  class MBaseFragment : Fragment() {
    var frgView : View ?= null//fragment View

    fun toast(message : String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
    fun toast(message : Int){
        Toast.makeText(context,getString(message), Toast.LENGTH_SHORT).show()
    }
}