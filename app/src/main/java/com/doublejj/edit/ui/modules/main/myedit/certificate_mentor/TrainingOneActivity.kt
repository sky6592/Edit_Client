package com.doublejj.edit.ui.modules.main.myedit.certificate_mentor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityTrainingOneBinding

class TrainingOneActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityTrainingOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_training_one)

        // add activity at sActivityList
        ApplicationClass.sActivityList.add(this)

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            val sendIntent = Intent(this, TrainingTwoActivity::class.java)
            startActivity(sendIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ApplicationClass.sActivityList.remove(this)
    }
}