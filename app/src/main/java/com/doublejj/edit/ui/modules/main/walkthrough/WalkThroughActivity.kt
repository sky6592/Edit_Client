package com.doublejj.edit.ui.modules.main.walkthrough

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.models.walkthrough.WalkThroughModel
import com.doublejj.edit.databinding.ActivityWalkThroughBinding
import com.doublejj.edit.ui.modules.main.login.LogInActivity
import com.doublejj.edit.ui.modules.main.signin.SignInActivity
import com.doublejj.edit.ui.modules.main.signup.infofirst.InfoFirstActivity
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager

class WalkThroughActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityWalkThroughBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_walk_through)
        mBinding.walkThroughActivity = this

        val imgModel = arrayListOf(
            WalkThroughModel(R.drawable.walkthorugh_img1, R.string.tv_walk_thorough_one.toString()),
            WalkThroughModel(R.drawable.walkthorugh_img2, R.string.tv_walk_thorough_two.toString()),
            WalkThroughModel(
                R.drawable.walkthrough_img3,
                R.string.tv_walk_thorough_three.toString()
            )
        )

        mBinding.autoViewPagerWalkThrough.adapter = WalkThroughAdapter(this, imgModel)
        mBinding.autoViewPagerWalkThrough.setInterval(3000)
        mBinding.autoViewPagerWalkThrough.setDirection(AutoScrollViewPager.Direction.RIGHT)
        mBinding.autoViewPagerWalkThrough.setCycle(true)
        mBinding.autoViewPagerWalkThrough.setBorderAnimation(true)
        mBinding.autoViewPagerWalkThrough.setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT)
        mBinding.autoViewPagerWalkThrough.startAutoScroll()
        mBinding.dotsIndicatorWalkThorugh.setViewPager(mBinding.autoViewPagerWalkThrough)
    }

    fun onMoveSignUp() {
        return startActivity(Intent(this, InfoFirstActivity::class.java))
    }

    fun onMoveLogIn() {
        return startActivity(Intent(this, LogInActivity::class.java))
    }
}