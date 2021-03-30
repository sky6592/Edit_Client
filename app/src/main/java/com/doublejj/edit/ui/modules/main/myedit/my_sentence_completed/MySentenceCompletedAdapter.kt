package com.doublejj.edit.ui.modules.main.myedit.my_sentence_completed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.sentence.DeletePublishedSentenceService
import com.doublejj.edit.data.api.services.sentence.DeletePublishedSentenceView
import com.doublejj.edit.data.models.ResultResponse
import com.doublejj.edit.data.models.my_sentence_completed.MySentenceCompletedResult
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomDialogFragment
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MySentenceCompletedAdapter(
    val context: Context,
    var sentenceDataList: MutableList<MySentenceCompletedResult>,
    val fm: FragmentManager
) : RecyclerView.Adapter<MySentenceCompletedAdapter.ViewHolder>(),
    DeletePublishedSentenceView {
    lateinit var parentView: ViewGroup

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MySentenceCompletedAdapter.ViewHolder {
        parentView = parent

        val inflater = LayoutInflater.from(parent.context)
        val itemView: View = inflater.inflate(R.layout.layout_my_sentence_completed, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MySentenceCompletedAdapter.ViewHolder, position: Int) {
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
                    DeletePublishedSentenceService(this@MySentenceCompletedAdapter).tryDeletePublishedSentence(
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

        // 자소서 종류
        holder.tvSelfWritingType.text = sentenceData.coverLetterCategoryName

        // before sentence
        holder.tvSentenceBeforeContent.text = sentenceData.coverLetterContent

        // after sentence
        holder.tvSentenceAfterContent.text = sentenceData.completedCoverLetterContent

    }

    override fun getItemCount(): Int {
        return sentenceDataList.size
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
        val tvSentenceBeforeContent: TextView = itemView.findViewById(R.id.tv_sentence_before_content)
        val tvSentenceAfterContent: TextView = itemView.findViewById(R.id.tv_sentence_after_content)
    }
}