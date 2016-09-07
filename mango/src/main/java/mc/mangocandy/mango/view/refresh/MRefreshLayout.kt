package mc.mangocandy.mango

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import com.cjj.MaterialRefreshLayout
import com.cjj.MaterialRefreshListener

/**
 * Created by MangoCandy on 2016/7/25.
 */
class MRefreshLayout : MaterialRefreshLayout {
    constructor(context: Context?) : super(context) {

    }
    internal var onRefreshListener : OnRefreshListener ?= null

    fun MsetOnRefreshListener (onRefreshListener: OnRefreshListener){
        this.onRefreshListener = onRefreshListener
        /////////////
        setMaterialRefreshListener(object : MaterialRefreshListener(){
            override fun onRefresh(materialRefreshLayout: MaterialRefreshLayout?) {
                onRefreshListener.onRefresh()
            }

            override fun onRefreshLoadMore(materialRefreshLayout: MaterialRefreshLayout?) {
                onRefreshListener.onLoadMore()
            }
        })
    }

    fun MfinishRefreshOrLoadMore(){
        Handler().postDelayed(Runnable {
            finishRefresh()
            finishRefreshLoadMore()
        },1000)
    }

    fun startRefresh(){
        Handler().postDelayed(
                {
                    autoRefresh()
                },200
        )
    }

    fun startLoadmore(){
        autoRefreshLoadMore()
    }

    fun MsetCanLoad (boolean: Boolean){
        setLoadMore(boolean)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    abstract class OnRefreshListener{
        abstract fun onRefresh()
        open fun onLoadMore(){}
    }
}