package com.doublejj.edit.ui.modules.main.home.today_sentence

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.report_sentence.ReportSentenceService
import com.doublejj.edit.data.api.services.report_sentence.ReportSentenceView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.sentence.SentenceData
import com.doublejj.edit.ui.modules.main.home.open_comment.OpenCommentFragment
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class SentenceAdapter(
    val context: Context,
    var sentenceDataList: MutableList<SentenceData>,
    val fm: FragmentManager
) : RecyclerView.Adapter<SentenceAdapter.ViewHolder>(), ReportSentenceView {
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
        /*// TODO : response에 userProfile 추가되면 주석 풀고 프로필 적용
        val characterResId = (context.applicationContext as ApplicationClass).getCharacterResId(sentenceData.userProfile)
        holder.ivCharacter.setImageResource(characterResId)*/
        holder.tvSentenceWriter.text = sentenceData.nickName
        holder.tvOccupationType.text = sentenceData.jobName
        holder.ibMenu.setOnClickListener {
            val dialog = CustomDialogFragment(
                R.string.tv_dialog_sentence_report_title,
                R.string.tv_dialog_sentence_report_content,
                R.string.tv_dialog_report,
                R.string.tv_dialog_dismiss
            )
            dialog.setDialogClickListener(object : CustomDialogClickListener {
                override fun onPositiveClick() {
                    // 해당 카드 신고 처리
                    ReportSentenceService(this@SentenceAdapter).tryReportSentence(sentenceData.coverLetterId)
                }
                override fun onNegativeClick() {
                }
            })
            dialog.show(fm, "CustomDialog")
        }
        holder.tvSelfWritingType.text = sentenceData.coverLetterCategoryName
        holder.tvSentenceContent.text = sentenceData.coverLetterContent
        holder.tbSympathy.isChecked = sentenceData.sympathy
        holder.tvSympathyCount.text = sentenceData.sympathiesCount.toString()
        holder.llBtnSympathy.setOnClickListener {
            // TODO : 해당 카드 공감 처리
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
            /*// TODO : response에 userProfile 추가되면 주석 풀고 프로필 적용
            bundle.putInt("ivCharacter", characterResId)*/
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
    }

    override fun onReportSentenceFailure(message: String) {
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