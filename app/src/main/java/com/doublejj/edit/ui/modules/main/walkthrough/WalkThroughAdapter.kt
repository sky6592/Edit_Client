package com.doublejj.edit.ui.modules.main.walkthrough

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.doublejj.edit.R
import com.doublejj.edit.data.models.walkthrough.WalkThroughModel
import com.doublejj.edit.databinding.ItemWalkThroughBinding


//Array<String> : 들어가야할내용 변경해야함!
class WalkThroughAdapter(
    private val context: Context,
    private val images: ArrayList<WalkThroughModel>
) :
    PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int = images.size

    //[how to use data binding in ViewPagerAdapter]
    //https://github.com/shanonim/Kotlin-DataBinding-ViewPager/blob/master/app/src/main/java/com/shanonim/viewpagersample/CustomPagerAdapter.kt
    //https://blog.yena.io/studynote/2019/11/13/Android-View-Pager-Basic.html
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemWalkThroughBinding.inflate(LayoutInflater.from(context), container, false)

        //맞는지 확인 필요함..!
        binding.ivItemWalkThrough.setImageResource(images[position].img)

        container?.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container?.removeView(`object` as LinearLayout)
    }
}