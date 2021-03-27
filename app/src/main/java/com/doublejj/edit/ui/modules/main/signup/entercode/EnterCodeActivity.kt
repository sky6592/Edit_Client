package com.doublejj.edit.ui.modules.main.signup.entercode

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.entercode.EnterCodeService
import com.doublejj.edit.data.api.services.entercode.EnterCodeView
import com.doublejj.edit.data.models.entercode.EnterCodeResponse
import com.doublejj.edit.databinding.ActivityEnterCodeBinding
import com.doublejj.edit.ui.modules.main.signup.emailcheck.EmailCheckActivity
import com.doublejj.edit.ui.modules.main.signup.slecttype.SelectTypeActivity
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment


class EnterCodeActivity : AppCompatActivity(), EnterCodeView {

    private lateinit var mBinding: ActivityEnterCodeBinding
//    private var mArrayList: ArrayList<String>? = null
    private lateinit var mArrayList: ArrayList<String>
    private lateinit var intentEmailCheck: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_enter_code)

        //전역변수
        intentEmailCheck = Intent(this, EmailCheckActivity::class.java)
        mArrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
        countDown("000300")

        //확인 버튼
        mBinding.btnEnterCode.setOnClickListener {
            if (mBinding.etEnterCode.text.isNotEmpty()) {
                Log.d("sky", mBinding.etEnterCode.text.toString())
                it.setBackgroundResource(R.color.purple)
                EnterCodeService(this).tryGetEnterCode(mBinding.etEnterCode.text.toString())
            } else {
                it.setBackgroundResource(R.color.very_light_pink)
            }
        }
    }

    //타이머
    private fun countDown(time: String) {
        var conversionTime: Long = 0

        // 1000 단위가 1초
        // 60000 단위가 1분
        // 60000 * 3600 = 1시간
        var getHour = time.substring(0, 2)
        var getMin = time.substring(2, 4)
        var getSecond = time.substring(4, 6)

        // "00"이 아니고, 첫번째 자리가 0 이면 제거
        if (getHour.substring(0, 1) === "0") {
            getHour = getHour.substring(1, 2)
        }
        if (getMin.substring(0, 1) === "0") {
            getMin = getMin.substring(1, 2)
        }
        if (getSecond.substring(0, 1) === "0") {
            getSecond = getSecond.substring(1, 2)
        }

        // 변환시간
        conversionTime =
            java.lang.Long.valueOf(getHour) * 1000 * 3600 + java.lang.Long.valueOf(getMin) * 60 * 1000 + java.lang.Long.valueOf(
                getSecond
            ) * 1000

        // 첫번쨰 인자 : 원하는 시간 (예를들어 30초면 30 x 1000(주기))
        // 두번쨰 인자 : 주기( 1000 = 1초)
        object : CountDownTimer(conversionTime, 1000) {
            // 특정 시간마다 뷰 변경
            override fun onTick(millisUntilFinished: Long) {

                // 시간단위
                var hour = (millisUntilFinished / (60 * 60 * 1000)).toString()

                // 분단위
                val getMin = millisUntilFinished - millisUntilFinished / (60 * 60 * 1000)
                var min = (getMin / (60 * 1000)).toString() // 몫

                // 초단위
                var second = (getMin % (60 * 1000) / 1000).toString() // 나머지

                // 밀리세컨드 단위
                val millis = (getMin % (60 * 1000) % 1000).toString() // 몫

                // 시간이 한자리면 0을 붙인다
                if (hour.length == 1) {
                    hour = "0$hour"
                }

                // 분이 한자리면 0을 붙인다
                if (min.length == 1) {
                    min = "0$min"
                }

                // 초가 한자리면 0을 붙인다
                if (second.length == 1) {
                    second = "0$second"
                }
                mBinding.tvCounterEnterCode.text = "$min:$second"
            }

            // 제한시간 종료
            override fun onFinish() {
                // TODO : 타이머 종료 후 다이얼로그 실행
                val dialog = CustomDialogFragment(
                    R.string.tv_dialog_2044_title_enter_code,
                    R.string.tv_dialog_2044_content_enter_code,
                    R.string.tv_dialog_check,
                    R.string.tv_dialog_dismiss
                )

                dialog.setDialogClickListener(object : CustomDialogClickListener {
                    //확인
                    override fun onPositiveClick() {
                        intentEmailCheck.putExtra("arrayList", mArrayList)
                        startActivity(intentEmailCheck)
                        finish()
                    }

                    //취소
                    override fun onNegativeClick() {
                    }
                })
                dialog.show(supportFragmentManager, "CustomDialog")
                //인증시간 만료 텍스트 변경
                mBinding.tvCaptionEnterCode.text = getString(R.string.tv_2044_enter_code)

            }
        }.start()
    }

    override fun onGetEnterCodeSuccess(response: EnterCodeResponse) {
        //인증코드 성공(3분)
        if (response.code == 1000) {
            val intent1000 = Intent(this, SelectTypeActivity::class.java)
            intent1000.putExtra("arrayList", mArrayList)
            startActivity(intent1000)
            finish()
        }

        //인증코드 실패(3분초과)
        if (response.code == 2044) {
            val dialog = CustomDialogFragment(
                R.string.tv_dialog_2044_title_enter_code,
                R.string.tv_dialog_2044_content_enter_code,
                R.string.tv_dialog_check,
                R.string.tv_dialog_dismiss
            )
            dialog.setDialogClickListener(object : CustomDialogClickListener {
                override fun onPositiveClick() {
                    intentEmailCheck.putExtra("arrayList", mArrayList)
                    startActivity(intentEmailCheck)
                    finish()
                }

                override fun onNegativeClick() {
                }
            })
            dialog.show(supportFragmentManager, "CustomDialog")

            mBinding.tvCaptionEnterCode.text = getString(R.string.tv_2044_enter_code)

        }

        //인증코드 실패(인증코드 다름)
        if (response.code == 3018) {
            Log.d("sky", "onGetEnterCodeSuccess- 3018")
            val dialog = CustomDialogFragment(
                R.string.tv_3018_enter_code,
                R.string.tv_dialog_3018_content_enter_code,
                R.string.tv_dialog_check,
                R.string.tv_dialog_dismiss_none
            )
            dialog.setDialogClickListener(object : CustomDialogClickListener {
                override fun onPositiveClick() {
//                    intentEmailCheck.putExtra("arrayList", mArrayList)
//                    startActivity(intentEmailCheck)
//                    finish()
                }

                override fun onNegativeClick() {
                }
            })
            dialog.show(supportFragmentManager, "CustomDialog")

            mBinding.tvCaptionEnterCode.text = getString(R.string.tv_2044_enter_code)
        }
    }

    override fun onGetEnterCodeFailure(message: String) {
        Log.d("sky", "onGetEnterCodeFailure")

    }
}