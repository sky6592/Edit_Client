package com.doublejj.edit.ui.modules.main.walkthrough

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doublejj.edit.R
import com.doublejj.edit.data.models.walkthrough.WalkThroughModel
import com.doublejj.edit.databinding.ActivityWalkThroughBinding
import com.doublejj.edit.ui.modules.main.login.LogInActivity
import com.doublejj.edit.ui.modules.main.signup.SignUpActivity
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager

class WalkThroughActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_walk_through)
        mBinding.walkThroughActivity = this

        val imgModel = arrayListOf(
            WalkThroughModel(R.drawable.walkthorugh_img1),
            WalkThroughModel(R.drawable.walkthorugh_img2),
            WalkThroughModel(R.drawable.walkthrough_img3)
        )

        mBinding.autoViewPagerWalkThrough.adapter = WalkThroughAdapter(this,imgModel)
        mBinding.autoViewPagerWalkThrough.setInterval(3000)
        mBinding.autoViewPagerWalkThrough.setDirection(AutoScrollViewPager.Direction.RIGHT)
        mBinding.autoViewPagerWalkThrough.setCycle(true)
        mBinding.autoViewPagerWalkThrough.setBorderAnimation(true)
        mBinding.autoViewPagerWalkThrough.setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT)
        mBinding.autoViewPagerWalkThrough.startAutoScroll()
        mBinding.dotsIndicatorWalkThorugh.setViewPager(mBinding.autoViewPagerWalkThrough)


    }

    fun onMoveSignUp() {
        return startActivity(Intent(this, SignUpActivity::class.java))
    }

    fun onMoveLogIn() {
        return startActivity(Intent(this, LogInActivity::class.java))
    }
}