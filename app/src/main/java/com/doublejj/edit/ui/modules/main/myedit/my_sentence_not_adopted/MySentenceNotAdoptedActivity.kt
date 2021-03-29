package com.doublejj.edit.ui.modules.main.myedit.my_sentence_not_adopted

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.USER_COLOR
import com.doublejj.edit.ApplicationClass.Companion.USER_EMOTION
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.my_sentence_not_adopted.MySentenceNotAdoptedService
import com.doublejj.edit.data.api.services.my_sentence_not_adopted.MySentenceNotAdoptedView
import com.doublejj.edit.data.api.services.writing_sentence.SentenceLimitService
import com.doublejj.edit.data.api.services.writing_sentence.SentenceLimitView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.my_sentence_not_adopted.MySentenceNotAdoptedResponse
import com.doublejj.edit.databinding.ActivityMySentenceNotAdoptedBinding
import com.doublejj.edit.ui.modules.main.home.writing_sentence.WritingSentenceActivity
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MySentenceNotAdoptedActivity : AppCompatActivity(),
    MySentenceNotAdoptedView, SentenceLimitView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityMySentenceNotAdoptedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_sentence_not_adopted)
        binding.lifecycleOwner = this

        /** get sentences from server **/
        getSentences()

        /** set adapter **/
        setAdapter()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.ibRefresh.setOnClickListener {
            // refresh data
            onResume()
        }

        // 내 프로필 캐릭터로 바꾸기
        val charColor = sSharedPreferences.getString(USER_COLOR, "purple").toString()
        val charEmotion = sSharedPreferences.getString(USER_EMOTION, "bigSmile").toString()
        val characterResId = (applicationContext as ApplicationClass).getCharacterResId(charColor, charEmotion)
        binding.ivMyCharacter.setImageResource(characterResId)

        binding.etInputSentence.setOnClickListener {
            when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
                "MENTEE" -> {
                    // 오늘 작성한 문장 개수 조회
                    SentenceLimitService(this).tryGetSentenceLimit()
                }
                "MENTOR" -> {
                    // 멘토 문장 작성 불가
                    // TODO : 스낵바 위치 bnv 위로 올리기
                    CustomSnackbar.make(binding.root, getString(R.string.snackbar_mentee_only), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    fun getSentences() {
        // TODO : 무한스크롤 처리
        MySentenceNotAdoptedService(this).tryGetMySentenceNotAdopted(page = 1)
    }

    fun setAdapter() {
        binding.rvSentence.layoutManager = LinearLayoutManager(this)
    }

    override fun onGetMySentenceNotAdoptedSuccess(response: MySentenceNotAdoptedResponse) {
        if (response.isSuccess) {
            binding.rvSentence.adapter = MySentenceNotAdoptedAdapter(this, response.result, supportFragmentManager)
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(this).dismiss()
    }

    override fun onGetMySentenceNotAdoptedFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(this).dismiss()
    }

    override fun onGetSentenceLimitSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            if (response.result < resources.getInteger(R.integer.length_limit_today_sentence)) {
                // 문장 작성 가능
                startActivity(Intent(this, WritingSentenceActivity::class.java))
            }
            else {
                // 문장 작성 가능 개수 초과
                // TODO : 스낵바 위치 bnv 위로 올리기
                CustomSnackbar.make(binding.root, getString(R.string.snackbar_limit_over),Snackbar.LENGTH_LONG).show()
            }

            CustomLoadingDialog(this).dismiss()
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()

            CustomLoadingDialog(this).dismiss()
        }
    }

    override fun onGetSentenceLimitFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        getSentences()
    }
}