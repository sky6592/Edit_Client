package com.doublejj.edit.ui.modules.main.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.doublejj.edit.R
import com.doublejj.edit.ui.modules.main.signup.infofirst.InfoFirstActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val animation_view: LottieAnimationView = findViewById(R.id.animation_view)
        animation_view.addAnimatorUpdateListener {
            if(animation_view.isAnimating){
                //애니메이션 끝나고 화면 넘어가기
                startActivity(Intent(this,InfoFirstActivity::class.java))
            }
        }


    }
}