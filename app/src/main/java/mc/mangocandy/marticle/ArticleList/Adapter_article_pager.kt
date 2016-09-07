package mc.mangocandy.marticle.ArticleList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import mc.mangocandy.marticle.Beans.ArticleType

/**
 * Created by MangoCandy on 2016/9/6.
 */
class Adapter_article_pager(fm: FragmentManager?, aTypes : List<ArticleType>) : FragmentPagerAdapter(fm) {
    var aTypes : List<ArticleType>
    init {
        this.aTypes = aTypes
    }
    override fun getCount(): Int {
        return aTypes.size
    }

    override fun getItem(position: Int): Fragment {
        var fragment = Frg_ArticleList()
        var bundle = Bundle()
        bundle.putSerializable("type",aTypes[position])
        fragment.arguments = bundle
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return aTypes[position].name
    }
}