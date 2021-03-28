package com.doublejj.edit.ui.modules.main.home.today_sentence

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.report_sentence.ReportSentenceService
import com.doublejj.edit.data.api.services.report_sentence.ReportSentenceView
import com.doublejj.edit.data.api.services.sentence.DeletePublishedSentenceService
import com.doublejj.edit.data.api.services.sentence.DeletePublishedSentenceView
import com.doublejj.edit.data.api.services.sentence.SympathizeSentenceService
import com.doublejj.edit.data.api.services.sentence.SympathizeSentenceView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.report_sentence.ReportSentenceRequest
import com.doublejj.edit.data.models.sentence.SentenceData
import com.doublejj.edit.data.models.sentence.SympathizeSentenceResponse
import com.doublejj.edit.ui.modules.main.home.open_comment.OpenCommentFragment
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class SentenceAdapter(
    val context: Context,
    var sentenceDataList: MutableList<SentenceData>,
    val fm: FragmentManager
) : RecyclerView.Adapter<SentenceAdapter.ViewHolder>(),
    ReportSentenceView, DeletePublishedSentenceView, SympathizeSentenceView {
    lateinit var parentView: ViewGroup

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SentenceAdapter.ViewHolder {
        parentView = parent

        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.layout_sentence, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SentenceAdapter.ViewHolder, position: Int) {
        var sentenceData = sentenceDataList.get(position)

        // 프로필 적용
        val characterResId = (context.applicationContext as ApplicationClass).getCharacterResId(sentenceData.userProfile)
        holder.ivCharacter.setImageResource(characterResId)

        holder.tvSentenceWriter.text = sentenceData.nickName
        holder.tvOccupationType.text = sentenceData.jobName

        // 내 문장일 경우 신고대신 삭제 처리
        if (sentenceData.isMine) {
            holder.ibMenu.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_delete))
        }
        holder.ibMenu.setOnClickListener {
            var dialog: CustomDialogFragment
            if (sentenceData.isMine) {
                dialog = CustomDialogFragment(
                    R.string.tv_dialog_open_sentence_title,
                    R.string.tv_dialog_open_sentence_content,
                    R.string.tv_dialog_delete,
                    R.string.tv_dialog_dismiss
                )
            }
            else {
                dialog = CustomDialogFragment(
                    R.string.tv_dialog_sentence_report_title,
                    R.string.tv_dialog_sentence_report_content,
                    R.string.tv_dialog_report,
                    R.string.tv_dialog_dismiss
                )
            }
            dialog.setDialogClickListener(object : CustomDialogClickListener {
                override fun onPositiveClick() {
                    // 내 문장일 경우 문장 삭제 API
                    if (sentenceData.isMine) {
                        // 문장 삭제 API
                        DeletePublishedSentenceService(this@SentenceAdapter).tryDeletePublishedSentence(
                            sentenceData.coverLetterId
                        )

                        // 리스트에서도 문장 삭제
                        sentenceDataList.remove(sentenceData)
                        // TODO : refresh()로 다시 리스트 보여주기
//                        refresh()
                    }
                    // 내 문장이 아닐 경우
                    else {
                        // 해당 문장 신고 API
                        ReportSentenceService(this@SentenceAdapter).tryReportSentence(
                            ReportSentenceRequest(sentenceData.coverLetterId)
                        )
                    }
                }
                override fun onNegativeClick() {
                }

            })
            dialog.show(fm, "CustomDialog")
        }

        holder.tvSelfWritingType.text = sentenceData.coverLetterCategoryName
        holder.tvSentenceContent.text = sentenceData.coverLetterContent
        holder.tbSympathy.isChecked = sentenceData.isSympathy
        holder.tvSympathyCount.text = sentenceData.sympathiesCount.toString()
        holder.llBtnSympathy.setOnClickListener {
            // 공감 처리
            SympathizeSentenceService(this).tryPatchSympathizeSentence(sentenceData.coverLetterId)
            val sympathyState = holder.tbSympathy.isChecked
            holder.tbSympathy.isChecked = !sympathyState
            if (!sympathyState) {
                sentenceData.sympathiesCount += 1
            }
            else {
                sentenceData.sympathiesCount -= 1
            }
            holder.tvSympathyCount.text = sentenceData.sympathiesCount.toString()
        }
        // TODO : ToggleButton 혼자만 눌리는 이슈 해결하기
        holder.llBtnOpenComment.setOnClickListener {
            // TODO : 해당 카드의 코멘트 보기 화면으로 이동
            val bundle = Bundle()
            bundle.putLong("coverLetterId", sentenceData.coverLetterId)
            bundle.putInt("ivCharacter", characterResId)
            bundle.putString("tvSentenceWriter", sentenceData.nickName)
            bundle.putString("tvOccupationType", sentenceData.jobName)
            bundle.putString("tvSelfWritingType", sentenceData.coverLetterCategoryName)
            bundle.putString("tvSentenceContent", sentenceData.coverLetterContent)

            fm.beginTransaction()
                .add(R.id.fl_home, OpenCommentFragment().apply {
                    arguments = bundle
                })
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return sentenceDataList.size
    }

    override fun onReportSentenceSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            CustomSnackbar.make(parentView, context.getString(R.string.snackbar_report), Snackbar.LENGTH_LONG).show()
        }
        else {
            CustomSnackbar.make(parentView, response.message.toString(), Snackbar.LENGTH_LONG).show()
        }
    }
    override fun onReportSentenceFailure(message: String) {
        CustomSnackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDeletePublishedSentenceSuccess(response: ResultResponse) {
        if (response.isSuccess) {
            CustomSnackbar.make(parentView, context.getString(R.string.snackbar_sentence_list_delete_mentee), Snackbar.LENGTH_LONG).show()
        }
        else {
            CustomSnackbar.make(parentView, response.message.toString(), Snackbar.LENGTH_LONG).show()
        }
    }
    override fun onDeletePublishedSentenceFailure(message: String) {
        CustomSnackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSympathizeSentenceSuccess(response: SympathizeSentenceResponse) {
        if (response.isSuccess) {
        }
        else {
            CustomSnackbar.make(parentView, response.message.toString(), Snackbar.LENGTH_LONG).show()
        }
    }
    override fun onSympathizeSentenceFailure(message: String) {
        CustomSnackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivCharacter: ImageView = itemView.findViewById(R.id.iv_character)
        val tvSentenceWriter: TextView = itemView.findViewById(R.id.tv_sentence_writer)
        val tvOccupationType: TextView = itemView.findViewById(R.id.tv_occupation_type)
        val ibMenu: ImageButton = itemView.findViewById(R.id.ib_menu)
        val tvSelfWritingType: TextView = itemView.findViewById(R.id.tv_self_writing_type)
        val tvSentenceContent: TextView = itemView.findViewById(R.id.tv_sentence_content)
        val llBtnSympathy: LinearLayout = itemView.findViewById(R.id.ll_btn_sympathy)
        val tbSympathy: ToggleButton = itemView.findViewById(R.id.tb_sympathy)
        val tvSympathyCount: TextView = itemView.findViewById(R.id.tv_sympathy_count)
        val llBtnOpenComment: LinearLayout = itemView.findViewById(R.id.ll_btn_open_comment)
    }
}