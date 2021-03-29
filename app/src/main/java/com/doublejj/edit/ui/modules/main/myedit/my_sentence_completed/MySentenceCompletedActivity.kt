package com.doublejj.edit.ui.modules.main.myedit.my_sentence_completed

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.USER_COLOR
import com.doublejj.edit.ApplicationClass.Companion.USER_EMOTION
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.my_sentence_completed.MySentenceCompletedService
import com.doublejj.edit.data.api.services.my_sentence_completed.MySentenceCompletedView
import com.doublejj.edit.data.models.my_sentence_completed.MySentenceCompletedResponse
import com.doublejj.edit.databinding.ActivityMySentenceCompletedBinding
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MySentenceCompletedActivity : AppCompatActivity(),
    MySentenceCompletedView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityMySentenceCompletedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_sentence_completed)
        binding.lifecycleOwner = this

        /** get sentences from server **/
        getSentences()

        /** set adapter **/
        setAdapter()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    fun getSentences() {
        // TODO : 무한스크롤 처리
        MySentenceCompletedService(this).tryGetMySentenceCompleted(page = 1)
    }

    fun setAdapter() {
        binding.rvSentence.layoutManager = LinearLayoutManager(this)
    }

    override fun onGetMySentenceCompleteSuccess(response: MySentenceCompletedResponse) {
        if (response.isSuccess) {
            binding.rvSentence.adapter = MySentenceCompletedAdapter(this, response.result, supportFragmentManager)
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(this).dismiss()
    }

    override fun onGetMySentenceCompleteFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(this).dismiss()
    }

    override fun onResume() {
        super.onResume()
        getSentences()
    }
}