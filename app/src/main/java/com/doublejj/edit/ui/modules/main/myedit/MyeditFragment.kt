package com.doublejj.edit.ui.modules.main.myedit

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.ApplicationClass.Companion.MENTOR_AUTH_CONFIRM
import com.doublejj.edit.ApplicationClass.Companion.USER_COLOR
import com.doublejj.edit.ApplicationClass.Companion.USER_EMOTION
import com.doublejj.edit.ApplicationClass.Companion.USER_NICKNAME
import com.doublejj.edit.ApplicationClass.Companion.USER_POSITION
import com.doublejj.edit.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.doublejj.edit.ApplicationClass.Companion.sSharedPreferences
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.certificate_mentor.AuthMentorStatusService
import com.doublejj.edit.data.api.services.certificate_mentor.AuthMentorStatusView
import com.doublejj.edit.data.api.services.logout.LogoutService
import com.doublejj.edit.data.api.services.logout.LogoutView
import com.doublejj.edit.data.api.services.profile.info.ProfileInfoService
import com.doublejj.edit.data.api.services.profile.info.ProfileInfoView
import com.doublejj.edit.data.models.BaseResponse
import com.doublejj.edit.data.models.certificate_mentor.AuthMentorStatusResponse
import com.doublejj.edit.data.models.profile.info.ProfileInfoResponse
import com.doublejj.edit.databinding.MyeditFragmentBinding
import com.doublejj.edit.ui.modules.main.myedit.certificate_mentor.CertificateInProgressActivity
import com.doublejj.edit.ui.modules.main.myedit.certificate_mentor.CertificateLogoutActivity
import com.doublejj.edit.ui.modules.main.myedit.certificate_mentor.CertificateMentorActivity
import com.doublejj.edit.ui.modules.main.myedit.certificate_mentor.CertificateRejectActivity
import com.doublejj.edit.ui.modules.main.myedit.manage_coin.ManageCoinActivity
import com.doublejj.edit.ui.modules.main.myedit.my_comment.MyCommentListActivity
import com.doublejj.edit.ui.modules.main.myedit.my_sentence_completed.MySentenceCompletedActivity
import com.doublejj.edit.ui.modules.main.myedit.my_sentence_not_adopted.MySentenceNotAdoptedActivity
import com.doublejj.edit.ui.modules.main.myedit.profile.ProfileActivity
import com.doublejj.edit.ui.modules.main.myedit.settings.SettingsActivity
import com.doublejj.edit.ui.modules.main.myedit.switch_position.SwitchToMenteeActivity
import com.doublejj.edit.ui.modules.main.myedit.switch_position.SwitchToMentorActivity
import com.doublejj.edit.ui.modules.main.splash.SplashActivity
import com.doublejj.edit.ui.utils.dialog.CustomLoadingDialog
import com.doublejj.edit.ui.utils.snackbar.CustomSnackbar
import com.google.android.material.snackbar.Snackbar

class MyeditFragment : Fragment(), ProfileInfoView, LogoutView, AuthMentorStatusView {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: MyeditFragmentBinding
    private lateinit var viewModel: MyeditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.myedit_fragment, container, false)
        viewModel = ViewModelProvider(this).get(MyeditViewModel::class.java)

        binding.myeditViewModel = viewModel
        binding.lifecycleOwner = this

        ProfileInfoService(this).tryGetProfileInfo()

        when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
            "MENTEE" -> {
                binding.tvPosition.text = "멘티님"
                binding.llBtnMenuSentence.visibility = View.VISIBLE
                binding.llBtnMenuComplete.visibility = View.VISIBLE
                binding.llBtnMenuCoin.visibility = View.GONE
                binding.llBtnMenuCommentList.visibility = View.GONE
                binding.llBtnCertificateMentor.visibility = View.GONE
            }
            "MENTOR" -> {
                binding.tvPosition.text = "멘토님"
                binding.llBtnMenuCoin.visibility = View.VISIBLE
                binding.llBtnMenuCommentList.visibility = View.VISIBLE
                binding.llBtnMenuSentence.visibility = View.GONE
                binding.llBtnMenuComplete.visibility = View.GONE

                // 멘토 인증하기 버튼 업데이트
                if (!sSharedPreferences.getBoolean(MENTOR_AUTH_CONFIRM, false)) {
                    // 인증 받지 않은 멘토라면 인증하기 버튼 보이기
                    binding.llBtnCertificateMentor.visibility = View.VISIBLE
                }
                else {
                    // 인증 받은 멘토라면 인증하기 버튼 감추기
                    binding.llBtnCertificateMentor.visibility = View.GONE
                }
            }
        }

        /** toolbar buttons **/
        binding.tvLogo.setOnClickListener {
            // scroll to top
            binding.nsvMyedit.smoothScrollTo(0, 0)
        }
        binding.ibSettings.setOnClickListener {
            val sendIntent = Intent(activity, SettingsActivity::class.java)
            startActivity(sendIntent)
        }

        /** menu buttons **/
        binding.ibMenuProfile.setOnClickListener {
            // 내 정보 페이지
            val sendIntent = Intent(activity, ProfileActivity::class.java)
            startActivity(sendIntent)
        }
        binding.ibMenuCoin.setOnClickListener {
            // 코인 페이지 (멘토)
            val sendIntent = Intent(activity, ManageCoinActivity::class.java)
            startActivity(sendIntent)
        }
        binding.ibMenuSympathy.setOnClickListener {
            // TODO : 공감 페이지
            CustomSnackbar.make(binding.root, getString(R.string.tv_to_be_develop), Snackbar.LENGTH_SHORT).show()
        }
        binding.ibMenuSentence.setOnClickListener {
            // 자소서 목록 페이지 (멘티)
            val sendIntent = Intent(activity, MySentenceNotAdoptedActivity::class.java)
            startActivity(sendIntent)
        }
        binding.ibMenuComplete.setOnClickListener {
            // 자소서 완성 페이지 (멘티)
            val sendIntent = Intent(activity, MySentenceCompletedActivity::class.java)
            startActivity(sendIntent)
        }
        binding.ibMenuCommentList.setOnClickListener {
            // TODO : 코멘트 목록 페이지 (멘토)
            val sendIntent = Intent(activity, MyCommentListActivity::class.java)
            startActivity(sendIntent)
            CustomSnackbar.make(binding.root, getString(R.string.tv_to_be_develop), Snackbar.LENGTH_SHORT).show()
        }

        // TODO : 임시저장 추후 업데이트
        binding.ibMenuTemp.setOnClickListener {
            // TODO : 임시 저장 페이지
            CustomSnackbar.make(binding.root, getString(R.string.tv_to_be_develop), Snackbar.LENGTH_SHORT).show()
        }

        /** position buttons **/
        binding.llBtnCertificateMentor.setOnClickListener {
            // pending 중이 아니라면 멘토 인증 페이지
            AuthMentorStatusService(this).tryGetAuthMentorStatus()

//            CustomLoadingDialog(requireContext()).show()
        }
        binding.llBtnSwitchPosition.setOnClickListener {
            when (sSharedPreferences.getString(USER_POSITION, "MENTEE")) {
                "MENTEE" -> startActivity(Intent(activity, SwitchToMentorActivity::class.java))
                "MENTOR" -> startActivity(Intent(activity, SwitchToMenteeActivity::class.java))
            }
        }

        /** logout buttons **/
        binding.btnLogout.setOnClickListener {
            // 로그아웃 API 적용
            LogoutService(this).tryPostLogout()
            CustomLoadingDialog(requireContext()).show()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        CustomLoadingDialog(requireContext()).dismiss()

        // 프로필 캐릭터 업데이트
        binding.tvNickname.text = sSharedPreferences.getString(USER_NICKNAME, null)
        binding.tvPosition.text = (requireContext().applicationContext as ApplicationClass).getPostionToString(
            sSharedPreferences.getString(USER_POSITION, null))
        binding.ivProfile.setImageResource((requireContext().applicationContext as ApplicationClass).getCharacterResId(
            sSharedPreferences.getString(USER_COLOR, null).toString(),
            sSharedPreferences.getString(USER_EMOTION, null).toString()
        ))

        // 멘토 인증하기 버튼 업데이트
        val isMentor = sSharedPreferences.getString(USER_POSITION, "MENTEE") == "MENTOR"
        val isMentorAuth = sSharedPreferences.getBoolean(MENTOR_AUTH_CONFIRM, false)
        if (isMentor) {
            if (!isMentorAuth) {
                // 인증 받지 않은 멘토라면 인증하기 버튼 보이기
                binding.llBtnCertificateMentor.visibility = View.VISIBLE
            }
            else {
                // 인증 받은 멘토라면 인증하기 버튼 감추기
                binding.llBtnCertificateMentor.visibility = View.GONE
            }
        }
    }

    override fun onProfileInfoSuccess(response: ProfileInfoResponse) {
        if (response.isSuccess) {
            val editor = sSharedPreferences.edit()
            editor.putString(USER_NICKNAME, response.result.nickName)
            editor.putString(USER_EMOTION, response.result.emotionName)
            editor.putString(USER_COLOR, response.result.colorName)
            editor.putString(USER_POSITION, response.result.userRole)
            editor.commit()
            editor.apply()

            binding.tvNickname.text = response.result.nickName
            binding.tvPosition.text = (requireContext().applicationContext as ApplicationClass).getPostionToString(response.result.userRole)
            binding.ivProfile.setImageResource((requireContext().applicationContext as ApplicationClass).getCharacterResId(response.result.colorName, response.result.emotionName))
        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(requireContext()).dismiss()
    }

    override fun onProfileInfoFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(requireContext()).dismiss()
    }

    override fun onAuthMentorStatusSuccess(response: AuthMentorStatusResponse) {
        if (response.isSuccess) {
            val editor = sSharedPreferences.edit()
            when (response.result.presentState) {
                "YES" -> {
                    CustomLoadingDialog(requireContext()).dismiss()
                    editor.putBoolean(MENTOR_AUTH_CONFIRM, true)
                    editor.commit()
                    editor.apply()
                    // 멘토 인증 완료 페이지로 이동
                    startActivity(Intent(activity, CertificateLogoutActivity::class.java))
                }
                "NO" -> {
                    CustomLoadingDialog(requireContext()).dismiss()
                    editor.putBoolean(MENTOR_AUTH_CONFIRM, false)
                    editor.commit()
                    editor.apply()
                    // 멘토 인증 거부 페이지로 이동
                    startActivity(Intent(activity, CertificateRejectActivity::class.java))
                }
                "WAITING" -> {
                    CustomLoadingDialog(requireContext()).dismiss()
                    // 멘토 인증 대기 페이지로 이동
                    editor.putBoolean(MENTOR_AUTH_CONFIRM, false)
                    editor.commit()
                    editor.apply()
                    startActivity(Intent(activity, CertificateInProgressActivity::class.java))
                }
            }
        }
        else {
            if (response.code == 3026) {
                CustomLoadingDialog(requireContext()).dismiss()
                // 멘토 인증 페이지로 이동
                startActivity(Intent(activity, CertificateMentorActivity::class.java))
            }
            else {
                CustomLoadingDialog(requireContext()).dismiss()
                CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAuthMentorStatusFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(requireContext()).dismiss()
    }

    override fun onPostLogoutSuccess(response: BaseResponse) {
        if (response.isSuccess) {
            val editor = sSharedPreferences.edit()
            editor.putString(X_ACCESS_TOKEN, null)
            editor.putString(USER_POSITION, null)
            editor.putBoolean(MENTOR_AUTH_CONFIRM, false)
            editor.putString(USER_NICKNAME, null)
            editor.putString(USER_EMOTION, null)
            editor.putString(USER_COLOR, null)
            editor.commit()
            editor.apply()

            // 초기화면으로 가기
            val sendIntent = Intent(activity, SplashActivity::class.java)
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(sendIntent)

        }
        else {
            CustomSnackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        CustomLoadingDialog(requireContext()).dismiss()
    }

    override fun onPostLogoutFailure(message: String) {
        CustomSnackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        CustomLoadingDialog(requireContext()).dismiss()
    }
}