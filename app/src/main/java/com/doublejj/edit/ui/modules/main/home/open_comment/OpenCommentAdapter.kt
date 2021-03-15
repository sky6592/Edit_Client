package com.doublejj.edit.ui.modules.main.home.open_comment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class OpenCommentAdapter(
    val context: Context,
    var commentDataList: MutableList<CommentData>,
    val fm: FragmentManager
) : RecyclerView.Adapter<OpenCommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OpenCommentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.layout_comment, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OpenCommentAdapter.ViewHolder, position: Int) {
        var commentData = commentDataList.get(position)

        val characterResId = (context.applicationContext as ApplicationClass).getCharacterResId(commentData.userProfile)
        holder.ivCharacter.setImageResource(characterResId)
        holder.tvSentenceWriter.text = commentData.nickName
        holder.tvOccupationType.text = commentData.jobName
        holder.ibMenu.setOnClickListener {
            val dialog = CustomDialogFragment(
                R.string.tv_dialog_sentence_report_title,
                R.string.tv_dialog_sentence_report_content,
                R.string.tv_dialog_report,
                R.string.tv_dialog_dismiss
            )
            dialog.setDialogClickListener(object : CustomDialogClickListener {
                override fun onPositiveClick() {
                    // TODO : 해당 카드 신고 처리
//                    commentData.commentId
                    CustomSnackbar.make(it, context.getString(R.string.snackbar_report), Snackbar.LENGTH_LONG).show()
                }
                override fun onNegativeClick() {
                }
            })
            dialog.show(fm, "CustomDialog")
        }

        holder.tvEvaluationSentence.text = commentData.sentenceEvaluation
        holder.tvEvaluationSentence.setTextColor(ContextCompat.getColor(context!!, (context.applicationContext as ApplicationClass).getEvaluationColorId(commentData.sentenceEvaluation)))
        holder.tvEvaluationConcretenessLogic.text = commentData.concretenessLogic
        holder.tvEvaluationConcretenessLogic.setTextColor(ContextCompat.getColor(context!!, (context.applicationContext as ApplicationClass).getEvaluationColorId(commentData.concretenessLogic)))
        holder.tvEvaluationSincerity.text = commentData.sincerity
        holder.tvEvaluationSincerity.setTextColor(ContextCompat.getColor(context!!, (context.applicationContext as ApplicationClass).getEvaluationColorId(commentData.sincerity)))
        holder.tvEvaluationActivity.text = commentData.activity
        holder.tvEvaluationActivity.setTextColor(ContextCompat.getColor(context!!, (context.applicationContext as ApplicationClass).getEvaluationColorId(commentData.activity)))
        holder.tvCommentContent.text = commentData.commentContent

        // TODO : 내 문장에 코멘트를 달아줬다면 감사합니다, 채택하기 visible
        /*if (commentData.userId == sSharedPreferences.getLong(USER_ID, "0")) {
            holder.tbThanks.visibility = View.VISIBLE
            holder.llBtnThanks.visibility = View.VISIBLE
            holder.tbAdoption.visibility = View.VISIBLE
            holder.llBtnAdoption.visibility = View.VISIBLE

            holder.tbThanks.isChecked = commentData.isThanked
            holder.llBtnThanks.setOnClickListener {
                // TODO : 해당 카드 감사해요 처리
                val state = holder.tbThanks.isChecked
                holder.tbThanks.isChecked = !state
                commentData.isThanked = !state
            }
            holder.tbAdoption.isChecked = commentData.isAdopted
            holder.llBtnAdoption.setOnClickListener {
                // TODO : 해당 카드 채택하기 처리
                val state = holder.tbAdoption.isChecked
                holder.tbAdoption.isChecked = !state
                commentData.isAdopted = !state
            }
            // TODO : ToggleButton 혼자만 눌리는 이슈 해결하기
        }*/
    }

    override fun getItemCount(): Int {
        return commentDataList.size
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

        val llBtnThanks: LinearLayout = itemView.findViewById(R.id.ll_btn_thanks)
        val tbThanks: ToggleButton = itemView.findViewById(R.id.tb_thanks)
        val llBtnAdoption: LinearLayout = itemView.findViewById(R.id.ll_btn_adoption)
        val tbAdoption: ToggleButton = itemView.findViewById(R.id.tb_adoption)
    }
}