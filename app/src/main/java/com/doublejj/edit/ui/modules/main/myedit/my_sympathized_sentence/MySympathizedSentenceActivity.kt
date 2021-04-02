package com.doublejj.edit.ui.modules.main.myedit.my_sympathized_sentence

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.my_sympathized_sentence.MySympathizedSentenceService
import com.doublejj.edit.data.api.services.my_sympathized_sentence.MySympathizedSentenceView
import com.doublejj.edit.data.models.my_sympathized_sentence.MySympathizedSentenceResponse
import com.doublejj.edit.databinding.ActivityMySympathizedSentenceBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MySympathizedSentenceActivity : AppCompatActivity(), MySympathizedSentenceView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityMySympathizedSentenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_sympathized_sentence)
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
    }

    fun getSentences() {
        // TODO : 무한스크롤 처리
        MySympathizedSentenceService(this).tryGetMySympathizedSentence(page = 1)
    }

    fun setAdapter() {
        binding.rvSentence.layoutManager = LinearLayoutManager(this)
    }

    override fun onGetMySympathizedSentenceSuccess(response: MySympathizedSentenceResponse) {
        if (response.isSuccess) {
            binding.rvSentence.adapter = SentenceActivityAdapter(applicationContext, response.result.coverLetters, supportFragmentManager)
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onGetMySympathizedSentenceFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        getSentences()
    }
}