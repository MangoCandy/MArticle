package mc.mangocandy.MToast

import android.content.Context
import android.widget.Toast

/**
 * Created by Administrator on 2016/7/22.
 */
class MToast{
    companion object{
        var toast:Toast ?= null
        fun show(context:Context,message:String){
            showToast(context,message)
        }
        fun show(context: Context,message: Int){
            showToast(context,context.resources.getString(message))
        }
        private fun showToast(context: Context,message: String){
            dismiss()
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT)
            toast!!.show()
        }

        private fun dismiss(){
            if(toast!=null){
                toast!!.cancel()
            }
        }
    }
}