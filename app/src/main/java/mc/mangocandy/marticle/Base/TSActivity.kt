package mc.mangocandy.marticle.Base

import android.os.Bundle
import mc.mangocandy.marticle.R

/**
 * Created by MangoCandy on 2016/9/7.
 */
open class TSActivity : MBaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.pink_TS)
    }
}