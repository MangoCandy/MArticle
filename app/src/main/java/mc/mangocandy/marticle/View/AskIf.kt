package com.ydwj.ailejia.View

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.KeyEvent
import android.view.LayoutInflater
import mc.mangocandy.marticle.R

/**
 * Created by Administrator on 2016/7/25.
 */
abstract class AskIf{
    var context : Context ?= null
    var message : String ?= null
    var cancelable : Boolean = true

    constructor(context: Context, message:String){
        this.context = context
         this.message = message
    }

    fun show(){
        var view = LayoutInflater.from(context).inflate(R.layout.dialog_view,null)
        var alertDialog : AlertDialog.Builder = AlertDialog.Builder(context!!)
                .setMessage(message)
                .setCustomTitle(view)
                .setOnCancelListener { onCancel() }
                .setPositiveButton("确认") { p0, p1 -> onDone() }
                .setNegativeButton("取消") { p0, p1 -> onCancel() }
                .setCancelable(cancelable)
                .setOnKeyListener { p0, p1, p2 -> true }
        alertDialog.show()

    }

    abstract fun onDone()
    abstract fun onCancel()
}