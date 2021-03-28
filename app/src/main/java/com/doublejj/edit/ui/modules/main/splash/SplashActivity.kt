package com.doublejj.edit.ui.modules.main.splash

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.airbnb.lottie.LottieAnimationView
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.splash.SplashService
import com.doublejj.edit.data.api.services.splash.SplashView
import com.doublejj.edit.data.models.splash.SplashResponse
import com.doublejj.edit.databinding.ActivitySplashBinding
import com.doublejj.edit.ui.modules.main.MainActivity
import com.doublejj.edit.ui.modules.main.signup.infofirst.InfoFirstActivity
import com.doublejj.edit.ui.modules.main.walkthrough.WalkThroughActivity

class SplashActivity : AppCompatActivity(), SplashView {
    private lateinit var mBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        mBinding.animationView.addAnimatorUpdateListener {
            if (mBinding.animationView.isAnimating) {
                Log.d("editors", "addAnimatorUpdateListener")
                SplashService(this).tryGetSplash()
            }
        }
    }

    override fun onGetSplashSuccess(response: SplashResponse) {
        Log.d("editors", "Splash - API성공")
        /** Success : Go Main **/
        if (response.code == 1000) {
            //jwt값 저장되어있는지 확인
            val spf = this.getSharedPreferences("EDIT", MODE_PRIVATE)
            val editor = ApplicationClass.sSharedPreferences.edit()
            val jwt = spf.getString(ApplicationClass.X_ACCESS_TOKEN, "")
            editor.putString(ApplicationClass.X_ACCESS_TOKEN, jwt)
            //멘토 인증 여부
            editor.putBoolean(
                ApplicationClass.MENTOR_AUTH_CONFIRM,
                response.result.isCertificatedMentor
            )
            editor.commit()
            editor.apply()
            //가입자(멘토/멘티)
            var intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
            finish()
        } else {
            //미가입자
            var intentWalkThrough = Intent(this, WalkThroughActivity::class.java)
            startActivity(intentWalkThrough)
            finish()
        }
    }

    override fun onGetSplashFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
        Log.d("editors", message)
    }
}