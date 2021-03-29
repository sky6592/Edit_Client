package com.doublejj.edit.ui.modules.main.myedit.my_sentence_not_adopted

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.lookup_comments_of_sentence.CommentsOfSentenceService
import com.doublejj.edit.data.api.services.lookup_comments_of_sentence.CommentsOfSentenceView
import com.doublejj.edit.data.api.services.my_sentence_not_adopted.SentenceToCompleteService
import com.doublejj.edit.data.api.services.my_sentence_not_adopted.SentenceToCompleteView
import com.doublejj.edit.data.api.services.sentence.DeletePublishedSentenceService
import com.doublejj.edit.data.api.services.sentence.DeletePublishedSentenceView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.lookup_comments_of_sentence.LookupCommentResponse
import com.doublejj.edit.data.models.my_sentence_not_adopted.MySentenceNotAdoptedResult
import com.doublejj.edit.data.models.my_sentence_not_adopted.SentenceToCompleteResponse
import com.doublejj.edit.ui.modules.main.home.open_comment.OpenCommentActivity
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MySentenceNotAdoptedAdapter(
    val context: Context,
    var sentenceDataList: MutableList<MySentenceNotAdoptedResult>,
    val fm: FragmentManager
) : RecyclerView.Adapter<MySentenceNotAdoptedAdapter.ViewHolder>(),
    SentenceToCompleteView, DeletePublishedSentenceView {
    lateinit var parentView: ViewGroup

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MySentenceNotAdoptedAdapter.ViewHolder {
        parentView = parent

        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.layout_my_sentence_not_adopted, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MySentenceNotAdoptedAdapter.ViewHolder, position: Int) {
        var sentenceData = sentenceDataList.get(position)

        // 프로필 적용
        val characterResId = (context.applicationContext as ApplicationClass).getCharacterResId(sentenceData.userProfile)
        holder.ivCharacter.setImageResource(characterResId)

        holder.tvSentenceWriter.text = sentenceData.nickName
        holder.tvOccupationType.text = sentenceData.jobName

        // 내 문장 삭제 처리
        holder.ibMenu.setOnClickListener {
            val dialog = CustomDialogFragment(
                R.string.tv_dialog_open_sentence_title,
                R.string.tv_dialog_open_sentence_content,
                R.string.tv_dialog_delete,
                R.string.tv_dialog_dismiss
            )
            dialog.setDialogClickListener(object : CustomDialogClickListener {
                override fun onPositiveClick() {
                    // 문장 삭제 API
                    DeletePublishedSentenceService(this@MySentenceNotAdoptedAdapter).tryDeletePublishedSentence(
                        sentenceData.coverLetterId
                    )

                    // 삭제 후 리스트에서 바로 지우기
                    sentenceDataList.remove(sentenceData)
                    notifyDataSetChanged()
                }
                override fun onNegativeClick() {
                }

            })
            dialog.show(fm, "CustomDialog")
        }

        holder.tvSelfWritingType.text = sentenceData.coverLetterCategoryName
        holder.tvSentenceContent.text = sentenceData.coverLetterContent

        holder.llBtnComplete.setOnClickListener {
            // 채택여부 받아서 문장 완성할 수 있는지, 없는지 다이얼로그 띄우기
            SentenceToCompleteService(this).tryGetSentenceToComplete(
                sentenceData.coverLetterId,
                position
            )
        }
        // TODO : ToggleButton 혼자만 눌리는 이슈 해결하기

        holder.llBtnOpenComment.setOnClickListener {
            // 해당 문장의 코멘트 보기 화면으로 이동
            val sendIntent = Intent(context, OpenCommentActivity::class.java)
            sendIntent.putExtra("originalCoverLetterId", sentenceData.coverLetterId)
            sendIntent.putExtra("ivCharacter", sentenceData.userProfile)
            sendIntent.putExtra("nickName", sentenceData.nickName)
            sendIntent.putExtra("jobName", sentenceData.jobName)
            sendIntent.putExtra("originalCoverLetterCategoryName", sentenceData.coverLetterCategoryName)
            sendIntent.putExtra("originalCoverLetterContent", sentenceData.coverLetterContent)

            Log.d("lalala", "sentenceId: ${sentenceData.coverLetterId}, nickName: ${sentenceData.nickName}")

            context.startActivity(sendIntent)
        }
    }

    override fun getItemCount(): Int {
        return sentenceDataList.size
    }

    override fun onGetSentenceToCompleteSuccess(response: SentenceToCompleteResponse, position: Int) {
        if (response.isSuccess) {
            // 문장 완성하기 페이지로 이동
            val sendIntent = Intent(context, CompleteWritingSentenceActivity::class.java)
            sendIntent.putExtra("originalCoverLetterId", response.result.originalCoverLetterId)
            sendIntent.putExtra("originalCoverLetterCategoryName", response.result.originalCoverLetterCategoryName)
            sendIntent.putExtra("originalCoverLetterContent", response.result.originalCoverLetterContent)
            sendIntent.putExtra("adoptedCommentContent", response.result.adoptedCommentContent)

            context.startActivity(sendIntent)

            // TODO : 삭제 후 리스트에서 바로 지우기 (보류)
//            sentenceDataList.removeAt(response.position!!)
            notifyDataSetChanged()
        }
        else {
            if (response.code == 3330) {
                val dialog = CustomDialogFragment(
                    R.string.tv_dialog_self_writing_title,
                    R.string.tv_dialog_self_writing_content,
                    R.string.tv_dialog_check,
                    null
                )
                dialog.setDialogClickListener(object : CustomDialogClickListener {
                    override fun onPositiveClick() {
                        // nothing
                    }
                    override fun onNegativeClick() {
                    }
                })
                dialog.show(fm, "CustomDialog")
            }
            else {
                CustomSnackbar.make(parentView, response.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }

        CustomLoadingDialog(context).dismiss()
    }

    override fun onGetSentenceToCompleteFailure(message: String) {
        CustomSnackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(context).dismiss()
    }

    override fun onDeletePublishedSentenceSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            CustomSnackbar.make(parentView, context.getString(R.string.snackbar_sentence_list_delete_mentee), Snackbar.LENGTH_LONG).show()
        }
        else {
            CustomSnackbar.make(parentView, response.message.toString(), Snackbar.LENGTH_LONG).show()
        }

        CustomLoadingDialog(context).dismiss()
    }
    override fun onDeletePublishedSentenceFailure(message: String) {
        CustomSnackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(context).dismiss()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivCharacter: ImageView = itemView.findViewById(R.id.iv_character)
        val tvSentenceWriter: TextView = itemView.findViewById(R.id.tv_sentence_writer)
        val tvOccupationType: TextView = itemView.findViewById(R.id.tv_occupation_type)
        val ibMenu: ImageButton = itemView.findViewById(R.id.ib_menu)
        val tvSelfWritingType: TextView = itemView.findViewById(R.id.tv_self_writing_type)
        val tvSentenceContent: TextView = itemView.findViewById(R.id.tv_sentence_content)
        val llBtnComplete: LinearLayout = itemView.findViewById(R.id.ll_btn_complete)
        val llBtnOpenComment: LinearLayout = itemView.findViewById(R.id.ll_btn_open_comment)
    }
}