package com.ydwj.mangotools.MView

import android.app.ProgressDialog
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import mc.mangocandy.mango.R


/**
 * Created by Administrator on 2016/7/22.
 */
class MProgressDialog{
    companion object{
        var progressDialog:ProgressDialog?= null

        fun show(context:Context,message:String) : ProgressDialog{
            showmessage(context,message)
            return progressDialog!!
        }

        fun show(context:Context,message:Int){
            showmessage(context,context.resources.getString(message))
        }

        private fun showmessage(context: Context,message:String){
            progressDialog = ProgressDialog(context)
            var view = LayoutInflater.from(context).inflate(R.layout.dialog_view,null)
            progressDialog!!.setCustomTitle(view)
            progressDialog!!.setMessage(message)
            progressDialog!!.show()
        }

        fun cantClose(boolean: Boolean){
            progressDialog!!.setCancelable(boolean)
        }

        fun dismiss(){
            if(progressDialog != null && progressDialog!!.isShowing){
                Handler().postDelayed({
                    progressDialog!!.dismiss()
                },200)
            }
        }
    }
}