package com.doublejj.edit.ui.modules.main.myedit.my_comment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.comment.*
import com.doublejj.edit.data.api.services.lookup_sentence_of_comment.SentencesOfCommentService
import com.doublejj.edit.data.api.services.lookup_sentence_of_comment.SentencesOfCommentView
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.data.models.lookup_sentence_mentor.LookupSentenceMentorResponse
import com.doublejj.edit.ui.modules.main.myedit.my_comment.open_comment.LookupMyCommentActivity
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MyCommentAdapter(
    val context: Context,
    var commentDataList: MutableList<CommentData>,
    val fm: FragmentManager
) : RecyclerView.Adapter<MyCommentAdapter.ViewHolder>(), SentencesOfCommentView {
    lateinit var parentView: ViewGroup
    lateinit var sendIntent: Intent

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCommentAdapter.ViewHolder {
        parentView = parent
        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.layout_comment, parent, false)
        sendIntent = Intent(context, LookupMyCommentActivity::class.java)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyCommentAdapter.ViewHolder, position: Int) {
        var commentData = commentDataList.get(position)

        val characterResId = (context.applicationContext as ApplicationClass).getCharacterResId(commentData.userProfile)
        holder.ivCharacter.setImageResource(characterResId)
        holder.tvSentenceWriter.text = commentData.nickName
        holder.tvOccupationType.text = commentData.jobName

        holder.ibMenu.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_delete))
        holder.ibMenu.setOnClickListener {
            val dialog = CustomDialogFragment(
                R.string.tv_dialog_open_comment_title,
                R.string.tv_dialog_open_comment_content,
                R.string.tv_dialog_delete,
                R.string.tv_dialog_dismiss
            )
            dialog.setDialogClickListener(object : CustomDialogClickListener, DeleteCommentView {
                override fun onPositiveClick() {
                    // 해당 코멘트 삭제 처리
                    DeleteCommentService(this).tryDeleteComment(
                        commentId = commentData.commentId
                    )
                    // 삭제 후 리스트에서 바로 지우기
                    commentDataList.remove(commentData)
                    notifyDataSetChanged()
                }
                override fun onNegativeClick() {
                }

                override fun onDeleteCommentSuccess(response: BaseResponse) {
                    if (response.isSuccess) {
                        CustomSnackbar.make(it, context.getString(R.string.snackbar_comment_list_delete_mentor), Snackbar.LENGTH_LONG).show()
                    }
                    else {
                        CustomSnackbar.make(it, response.message.toString(), Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onDeleteCommentFailure(message: String) {
                    CustomSnackbar.make(it, message, Snackbar.LENGTH_LONG).show()
                }
            })
            dialog.show(fm, "CustomDialog")
        }

        holder.tvEvaluationSentence.text = commentData.sentenceEvaluation
        holder.tvEvaluationSentence.setTextColor(ContextCompat.getColor(context, (context.applicationContext as ApplicationClass).getEvaluationColorId(commentData.sentenceEvaluation)))
        holder.tvEvaluationConcretenessLogic.text = commentData.concretenessLogic
        holder.tvEvaluationConcretenessLogic.setTextColor(ContextCompat.getColor(context, (context.applicationContext as ApplicationClass).getEvaluationColorId(commentData.concretenessLogic)))
        holder.tvEvaluationSincerity.text = commentData.sincerity
        holder.tvEvaluationSincerity.setTextColor(ContextCompat.getColor(context, (context.applicationContext as ApplicationClass).getEvaluationColorId(commentData.sincerity)))
        holder.tvEvaluationActivity.text = commentData.activity
        holder.tvEvaluationActivity.setTextColor(ContextCompat.getColor(context, (context.applicationContext as ApplicationClass).getEvaluationColorId(commentData.activity)))
        holder.tvCommentContent.text = commentData.commentContent

        holder.llBtnOpenSentence.visibility = View.VISIBLE
        holder.llBtnOpenSentence.setOnClickListener {
            // 문장 보기 API 적용
            SentencesOfCommentService(this).tryGetSentencesOfComment(
                commentId = commentData.commentId
            )
        }
    }

    override fun getItemCount(): Int {
        return commentDataList.size
    }

    override fun onGetSentencesOfCommentSuccess(response: LookupSentenceMentorResponse) {
        if (response.isSuccess) {
            // TODO : response 통일하면 response.result로 바꾸기
            sendIntent.putExtra("coverLetterId", response.result.coverLetterRes.coverLetterId)
            val chracterSentenceStr = response.result.coverLetterRes.userInfo.colorName + "/" + response.result.coverLetterRes.userInfo.emotionName
            sendIntent.putExtra("userProfile", chracterSentenceStr)
            sendIntent.putExtra("nickName", response.result.coverLetterRes.userInfo.nickName)
            sendIntent.putExtra("jobName", response.result.coverLetterRes.userInfo.jobName)
            sendIntent.putExtra("coverLetterCategoryName", response.result.coverLetterRes.coverLetterCategoryName)
            sendIntent.putExtra("coverLetterContent", response.result.coverLetterRes.coverLetterContent)

            sendIntent.putExtra("commentId", response.result.commentRes.commentInfo.commentId)
            val chracterCommentStr = response.result.commentRes.userInfo.colorName + "/" + response.result.commentRes.userInfo.emotionName
            sendIntent.putExtra("commentUserProfile", chracterCommentStr)
            sendIntent.putExtra("commentNickName", response.result.commentRes.userInfo.nickName)
            sendIntent.putExtra("commentJobName", response.result.commentRes.userInfo.jobName)
            sendIntent.putExtra("commentContent", response.result.commentRes.commentInfo.commentContent)
            sendIntent.putExtra("sentenceEvaluation", response.result.commentRes.commentInfo.sentenceEvaluation)
            sendIntent.putExtra("activity", response.result.commentRes.commentInfo.activity)
            sendIntent.putExtra("sincerity", response.result.commentRes.commentInfo.sincerity)
            sendIntent.putExtra("concretenessLogic", response.result.commentRes.commentInfo.concretenessLogic)

            context.startActivity(sendIntent)
        }
        else {
            CustomSnackbar.make(parentView, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(parentView.context).dismiss()
    }

    override fun onGetSentencesOfCommentFailure(message: String) {
        CustomSnackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(parentView.context).dismiss()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivCharacter: ImageView = itemView.findViewById(R.id.iv_character)
        val tvSentenceWriter: TextView = itemView.findViewById(R.id.tv_sentence_writer)
        val tvOccupationType: TextView = itemView.findViewById(R.id.tv_occupation_type)
        val ibMenu: ImageButton = itemView.findViewById(R.id.ib_menu)

        val tvEvaluationSentence: TextView = itemView.findViewById(R.id.tv_evaluation_value_0)
        val tvEvaluationConcretenessLogic: TextView = itemView.findViewById(R.id.tv_evaluation_value_1)
        val tvEvaluationSincerity: TextView = itemView.findViewById(R.id.tv_evaluation_value_2)
        val tvEvaluationActivity: TextView = itemView.findViewById(R.id.tv_evaluation_value_3)
        val tvCommentContent: TextView = itemView.findViewById(R.id.tv_comment_content)

        val llBtnOpenSentence: LinearLayout = itemView.findViewById(R.id.ll_btn_open_sentence)
    }
}