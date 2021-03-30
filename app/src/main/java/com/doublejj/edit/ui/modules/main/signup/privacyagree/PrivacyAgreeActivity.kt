package com.doublejj.edit.ui.modules.main.signup.privacyagree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityPrivacyAgreeBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.ui.modules.main.signup.infofirst.InfoFirstActivity
import com.doublejj.edit.ui.modules.main.signup.infosecond.InfoSecondActivity
import com.doublejj.edit.ui.modules.main.walkthrough.WalkThroughActivity

class PrivacyAgreeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPrivacyAgreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_agree)

        //Intent - ArrayList 저장
        var arrayList = intent.getSerializableExtra("arrayList") as ArrayList<String>
        Log.d("sky", arrayList.toString())

        //< 클릭
        mBinding.ivBackPrivacyAgree.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val binding: DialogEmailFindBinding = DialogEmailFindBinding.inflate(layoutInflater)

            //Dialog - Title
            binding.tvDialogTitleEmailFind.text =
                getString(R.string.tv_dialog_title_back_press)
            //Dialog - Content
            binding.tvDialogContentEmailFind.text =
                getString(R.string.tv_dialog_content_back_press)
            binding.tvDialogApiEmailFind.visibility = View.GONE
            //Dialog - 확인 버튼
            builder.setPositiveButton(getString(R.string.tv_dialog_confirm)) { _, _ ->
                val intentWalkThrough = Intent(this, WalkThroughActivity::class.java)
                startActivity(intentWalkThrough)
                finishAffinity()
            }
            builder.setNegativeButton(getString(R.string.tv_dialog_dismiss)) { _, _ ->

            }
            builder.setView(binding.root).show()
        }

        //버튼 클릭
        mBinding.btnPrivacyAgree.setOnClickListener {
            val intentInfoSecondActivity = Intent(this, InfoSecondActivity::class.java)
            intentInfoSecondActivity.putExtra("arrayList", arrayList)
            startActivity(intentInfoSecondActivity)
            finish()
        }
    }

    fun onDialog(): AlertDialog? {
        val builder = AlertDialog.Builder(this)
        val binding: DialogEmailFindBinding = DialogEmailFindBinding.inflate(layoutInflater)


        return builder.setView(binding.root).show()

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val builder = AlertDialog.Builder(this)
        val binding: DialogEmailFindBinding = DialogEmailFindBinding.inflate(layoutInflater)

        //Dialog - Title
        binding.tvDialogTitleEmailFind.text =
            getString(R.string.tv_dialog_title_back_press)
        //Dialog - Content
        binding.tvDialogContentEmailFind.text =
            getString(R.string.tv_dialog_content_back_press)
        binding.tvDialogApiEmailFind.visibility = View.GONE
        //Dialog - 확인 버튼
        builder.setPositiveButton(getString(R.string.tv_dialog_confirm)) { _, _ ->
            val intentWalkThrough = Intent(this, WalkThroughActivity::class.java)
            startActivity(intentWalkThrough)
            finishAffinity()
        }
        builder.setNegativeButton(getString(R.string.tv_dialog_dismiss)) { _, _ ->

        }
        builder.setView(binding.root).show()
    }
}