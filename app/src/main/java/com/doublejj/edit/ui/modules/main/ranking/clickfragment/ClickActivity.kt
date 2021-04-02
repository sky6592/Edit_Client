package com.doublejj.edit.ui.modules.main.ranking.clickfragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityClickBinding
import com.doublejj.edit.databinding.DialogEmailFindBinding
import com.doublejj.edit.ui.modules.main.walkthrough.WalkThroughActivity

class ClickActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityClickBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_click)

        //뒤로가기 버튼
        mBinding.ivBackClick.setOnClickListener {
            finish()
        }

        mBinding.ivClick.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val binding: DialogEmailFindBinding = DialogEmailFindBinding.inflate(layoutInflater)

            //Dialog - Title
            binding.tvDialogTitleEmailFind.text =
                "개발중입니다!"
            //Dialog - Content
            binding.tvDialogContentEmailFind.text =
                "더 좋은 화면을 제공드리기 위해\n추가 개발중입니다!"
            binding.tvDialogApiEmailFind.visibility = View.GONE
            //Dialog - 확인 버튼
            builder.setPositiveButton(getString(R.string.tv_dialog_confirm)) { _, _ ->
                val intentWalkThrough = Intent(this, WalkThroughActivity::class.java)
                startActivity(intentWalkThrough)
                finishAffinity()
            }

            builder.setView(binding.root).show()
        }
    }
}