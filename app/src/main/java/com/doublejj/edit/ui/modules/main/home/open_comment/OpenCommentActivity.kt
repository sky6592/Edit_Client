package com.doublejj.edit.ui.modules.main.home.open_comment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.lookup_comments_of_sentence.CommentsOfSentenceService
import com.doublejj.edit.data.api.services.lookup_comments_of_sentence.CommentsOfSentenceView
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.data.models.lookup_comments_of_sentence.LookupCommentResponse
import com.doublejj.edit.databinding.ActivityOpenCommentBinding
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class OpenCommentActivity : AppCompatActivity(), CommentsOfSentenceView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityOpenCommentBinding
    var sentenceId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_comment)
        binding.lifecycleOwner = this

        /** get comments from server **/
        sentenceId = intent.getLongExtra("originalCoverLetterId", 0L)
        getComments(sentenceId)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }
        binding.ibRefresh.setOnClickListener {
            // refresh data
            onResume()
        }

        /** floating button **/
        binding.fabMentor.visibility = View.GONE
        binding.fabMentor.isEnabled = false


        /** mentee's sentence **/
        placeSentenceFromBundle()
        binding.ibMenu.visibility = View.GONE
    }


    fun placeSentenceFromBundle() {
        binding.ivCharacter.setImageDrawable(ContextCompat.getDrawable(this, intent.getIntExtra("ivCharacter", R.drawable.icon_char_purple_active_0)))
        binding.tvSentenceWriter.text = intent.getStringExtra("nickName").toString()
        binding.tvOccupationType.text = intent.getStringExtra("jobName").toString()
        binding.tvSelfWritingType.text = intent.getStringExtra("originalCoverLetterCategoryName").toString()
        binding.tvSentenceContent.text = intent.getStringExtra("originalCoverLetterContent").toString()
    }

    fun getComments(sentenceId: Long) {
        // 코멘트 포함
        // TODO : 무한스크롤 처리
        CommentsOfSentenceService(this).tryGetCommentsOfSentence(
            sentenceId = sentenceId,
            page = 1
        )
    }

    fun setAdapter(commentDataList: MutableList<CommentData>) {
        // 내 문장인지 같이 전달
        val isMine = true

        binding.rvComment.layoutManager = LinearLayoutManager(applicationContext)
        binding.rvComment.adapter = OpenCommentAdapter(applicationContext, commentDataList, isMine, supportFragmentManager)

    }

    override fun onGetCommentsOfSentenceSuccess(response: LookupCommentResponse) {
        if (response.isSuccess) {
            val commentDataList = response.result.commentInfos
            if (commentDataList.size > 0) {
                binding.llZeroComment.visibility = View.GONE
                binding.rvComment.visibility = View.VISIBLE
                setAdapter(commentDataList)
            }
            else {
                binding.llZeroComment.visibility = View.VISIBLE
                binding.rvComment.visibility = View.GONE
            }
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onGetCommentsOfSentenceFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        getComments(sentenceId)
    }
}