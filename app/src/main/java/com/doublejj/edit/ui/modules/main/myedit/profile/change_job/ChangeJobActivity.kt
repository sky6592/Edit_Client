package com.doublejj.edit.ui.modules.main.myedit.profile.change_job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass.Companion.sActivityList
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.profile.change_job.ChangeJobService
import com.doublejj.edit.data.api.services.profile.change_job.ChangeJobView
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.profile.change_job.ChangeJobRequest
import com.doublejj.edit.databinding.ActivityChangeJobBinding
import com.doublejj.edit.ui.utils.dialog.CustomDialogClickListener
import com.doublejj.edit.ui.utils.dialog.CustomRadioDialogFragment
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class ChangeJobActivity : AppCompatActivity(), ChangeJobView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityChangeJobBinding

    // 직군 기타 입력 정규식 : 영문 대소문자, 숫자, 한글 (숫자, 특수문자 불가)
    val jobPattern = "^([a-zA-Zㄱ-ㅎ가-힇]*)$"

    private var selectedId: Int? = null
    private var selectedStr: String? = null
    private var etcStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_job)

        // add activity at sActivityList
        sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        /** select job **/
        binding.tvJob.setOnClickListener {
            val dialog = CustomRadioDialogFragment("JOB", selectedId)
            
            dialog.setDialogClickListener(object : CustomDialogClickListener {
                override fun onPositiveClick() {
                    // 직군 선택한게 있다면 object 상위 클래스의 멤버에 저장
                    if (dialog.selectedId != null) {
                        selectedId = dialog.selectedId
                        selectedStr = getString(dialog.selectedStrId!!)

                        this@ChangeJobActivity.binding.tvJob.text = selectedStr
                        this@ChangeJobActivity.binding.tvJob.setTextColor(ContextCompat.getColor(applicationContext, R.color.mid_black))

                        // 기타 옵션 선택했을 경우 처리
                        if (dialog.selectedId == R.id.rb_job_12) {
                            visibleEtcEditText(true)
                            binding.btnSave.isEnabled = false
                        }
                        else {
                            visibleEtcEditText(false)
                            etcStr = null
                            binding.btnSave.isEnabled = true
                        }
                    }
                }
                override fun onNegativeClick() {
                    // 선택 취소
                    selectedId = null
                    selectedStr = null

                    // 선택해주세요 hint 다시 설정
                    this@ChangeJobActivity.binding.tvJob.text = getString(R.string.spinner_hint)
                    this@ChangeJobActivity.binding.tvJob.setTextColor(ContextCompat.getColor(applicationContext, R.color.gray_shadow))
                    
                    // 기타 옵션 닫아주기
                    visibleEtcEditText(false)
                    etcStr = null
                }
            })
            dialog.show(supportFragmentManager, "CustomRadioDialog")
        }

        /** job etc **/
        binding.etInputJobEtc.addTextChangedListener(object : TextWatcher {
            // gets triggered immediately after something is typed
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // gets triggered before the next input
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            // gets triggered during an input
            override fun afterTextChanged(s: Editable?) {
                // if not matches pattern
                if (!Pattern.matches(jobPattern, s.toString())) {
                    binding.tvInputJobEtcCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.red_light))
                    binding.btnSave.isEnabled = false
                }
                // if matches pattern
                else {
                    binding.tvInputJobEtcCaption.setTextColor(ContextCompat.getColor(applicationContext, R.color.gray_shadow))
                    binding.btnSave.isEnabled = true
                }
            }
        })

        binding.btnSave.setOnClickListener {
            if (binding.btnSave.isEnabled) {
                etcStr = binding.etInputJobEtc.text.toString()
                if (etcStr == "" || etcStr == null || selectedId != R.id.rb_job_12) etcStr = "NONE"
                if (selectedId != null) {
                    // 직군 변경 API 적용
                    ChangeJobService(this).tryPatchChangeJob(ChangeJobRequest(
                        jobName = selectedStr!!,
                        etcJobName = etcStr!!
                    ))
                }
            }
        }
    }

    fun visibleEtcEditText(visible: Boolean) {
        if (visible) {
            binding.llJobEtc.visibility = View.VISIBLE
            binding.llJobEtc.isEnabled = true
        }
        else {
            binding.llJobEtc.visibility = View.GONE
            binding.llJobEtc.isEnabled = false
        }
    }

    override fun onChangeJobSuccess(response: BaseResponse) {
        if (response.isSuccess) {
            // 내 정보로 돌아가기
            sActivityList.actFinish()
        }
    }

    override fun onChangeJobFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
    }

    override fun onDestroy() {
        super.onDestroy()
        sActivityList.remove(this)
    }
}