package com.doublejj.edit.ui.modules.main.myedit.my_comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.my_comment.MyCommentService
import com.doublejj.edit.data.api.services.my_comment.MyCommentView
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.data.models.my_comment.MyCommentResponse
import com.doublejj.edit.databinding.ActivityMyCommentListBinding
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MyCommentListActivity : AppCompatActivity(), MyCommentView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityMyCommentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_comment_list)
        binding.lifecycleOwner = this

        /** get comments from server **/
        getComments()

        /** set adapter **/
        setAdapter()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    fun getComments() {
        // TODO : 무한스크롤 처리
        MyCommentService(this).tryGetMyCommentList(page = 1)
    }

    fun setAdapter() {
        binding.rvComment.layoutManager = LinearLayoutManager(this)
    }

    override fun onGetMyCommentSuccess(response: MyCommentResponse) {
        if (response.isSuccess) {
            // TODO : Loading dialog 띄우기
            var commentDataList = mutableListOf<CommentData>()
            for (comment in response.result) {
                commentDataList.add(CommentData(
                    commentId = comment.commentInfo.commentId,
                    userProfile = comment.userInfo.colorName+"/"+comment.userInfo.emotionName,
                    nickName = comment.userInfo.nickName,
                    jobName = comment.userInfo.jobName,
                    sentenceEvaluation = comment.commentInfo.sentenceEvaluation,
                    concretenessLogic = comment.commentInfo.concretenessLogic,
                    sincerity = comment.commentInfo.sincerity,
                    activity = comment.commentInfo.activity,

                    // not used data
                    commentContent = comment.commentInfo.commentContent,
                    isAdopted = "",
                    isAppreciated = false,
                    isMine = false
                ))
            }

            // TODO : response 통일하면 response.result로 바꾸기
            binding.rvComment.adapter = MyCommentAdapter(this, commentDataList, supportFragmentManager)
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(this).dismiss()
    }

    override fun onGetMyCommentFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(this).dismiss()
    }

    override fun onResume() {
        super.onResume()
        getComments()
    }
}