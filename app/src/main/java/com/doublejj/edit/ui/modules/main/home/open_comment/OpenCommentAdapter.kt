package com.doublejj.edit.ui.modules.main.home.open_comment

import android.content.Context
import android.util.Log
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
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.data.models.comment.ReportCommentRequest
import com.doublejj.edit.data.models.comment.ThanksCommentResponse
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class OpenCommentAdapter(
    val context: Context,
    var commentDataList: MutableList<CommentData>,
    val isMySentence: Boolean,
    val fm: FragmentManager
) : RecyclerView.Adapter<OpenCommentAdapter.ViewHolder>(),
    ThanksCommentView, AdoptCommentView {
    lateinit var parentView: ViewGroup
    var adoptedClickCount = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OpenCommentAdapter.ViewHolder {
        parentView = parent
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

        val isMine = commentData.isMine
        if (isMine) holder.ibMenu.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_delete))
        holder.ibMenu.setOnClickListener {
            // ??? ??????????????? ????????????
            if (isMine) {
                val dialog = CustomDialogFragment(
                    R.string.tv_dialog_open_comment_title,
                    R.string.tv_dialog_open_comment_content,
                    R.string.tv_dialog_delete,
                    R.string.tv_dialog_dismiss
                )
                dialog.setDialogClickListener(object : CustomDialogClickListener, DeleteCommentView {
                    override fun onPositiveClick() {
                        // ?????? ????????? ?????? ??????
                        DeleteCommentService(this).tryDeleteComment(
                            commentId = commentData.commentId
                        )
                        // ?????? ??? ??????????????? ?????? ?????????
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
            // ??? ???????????? ???????????? ????????????
            else {
                val dialog = CustomDialogFragment(
                    R.string.tv_dialog_sentence_report_title,
                    R.string.tv_dialog_sentence_report_content,
                    R.string.tv_dialog_report,
                    R.string.tv_dialog_dismiss
                )
                dialog.setDialogClickListener(object : CustomDialogClickListener,
                    ReportCommentView {
                    override fun onPositiveClick() {
                        // ?????? ????????? ?????? ??????
                        ReportCommentService(this).tryReportComment(
                            ReportCommentRequest(
                                commentId = commentData.commentId
                            ))
                    }
                    override fun onNegativeClick() {
                    }

                    override fun onReportCommentSuccess(response: ResultResponse) {
                        if (response.isSuccess) {
                            CustomSnackbar.make(it, context.getString(R.string.snackbar_report), Snackbar.LENGTH_LONG).show()
                        }
                        else {
                            CustomSnackbar.make(it, response.message.toString(), Snackbar.LENGTH_LONG).show()
                        }
                    }
                    override fun onReportCommentFailure(message: String) {
                        CustomSnackbar.make(it, message, Snackbar.LENGTH_LONG).show()
                    }
                })
                dialog.show(fm, "CustomDialog")
            }
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

        // ??? ????????? ???????????? ??????????????? ???????????????, ???????????? visible
        if (isMySentence && !commentData.isMine) {
            holder.tbThanks.visibility = View.VISIBLE
            holder.llBtnThanks.visibility = View.VISIBLE
            holder.tbAdoption.visibility = View.VISIBLE
            holder.llBtnAdoption.visibility = View.VISIBLE

            holder.tbAdoption.isChecked = commentData.isAdopted == "YES"
            holder.tbThanks.isChecked = commentData.isAppreciated

            // ???????????? ?????? ?????? ??????
            holder.llBtnThanks.setOnClickListener {
                // ???????????? ?????? ?????? ??????
                var isAppreciated = commentData.isAppreciated
                holder.tbThanks.isChecked = !isAppreciated
                commentData.isAppreciated = !isAppreciated

                // ???????????? API ??????
                ThanksCommentService(this).tryThanksComment(
                    commentId = commentData.commentId
                )
                commentDataList.set(position, commentData)
                notifyDataSetChanged()
            }

            holder.llBtnAdoption.setOnClickListener {
                // ???????????? API ??????
                AdoptCommentService(this).tryAdoptComment(
                    commentId = commentData.commentId
                )
                if (adoptedClickCount == position) {
                    // TODO : ???????????? ?????? ?????? ?????? (?????? ????????? ??? ????????? ?????????)
                    // TODO : ???????????? ???????????? ?????? ????????? ?????????
                    holder.tbAdoption.isChecked = true
                    commentData.isAdopted = "YES"
                }
                commentDataList.set(position, commentData)
                notifyDataSetChanged()
            }
            // TODO : ToggleButton ????????? ????????? ?????? ????????????
        }
    }

    override fun getItemCount(): Int {
        return commentDataList.size
    }

    override fun onThanksCommentSuccess(response: ThanksCommentResponse) {
        if (response.isSuccess) {
        }
        else {
            CustomSnackbar.make(parentView, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(parentView.context).dismiss()
    }

    override fun onThanksCommentFailure(message: String) {
        CustomSnackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(parentView.context).dismiss()
    }

    override fun onAdoptCommentSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            this.adoptedClickCount += 1
        }
        else {
            this.adoptedClickCount += 1
            CustomSnackbar.make(parentView, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(parentView.context).dismiss()
    }

    override fun onAdoptCommentFailure(message: String) {
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

        val llBtnThanks: LinearLayout = itemView.findViewById(R.id.ll_btn_thanks)
        val tbThanks: ToggleButton = itemView.findViewById(R.id.tb_thanks)
        val llBtnAdoption: LinearLayout = itemView.findViewById(R.id.ll_btn_adoption)
        val tbAdoption: ToggleButton = itemView.findViewById(R.id.tb_adoption)
    }
}