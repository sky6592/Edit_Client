package com.doublejj.edit.ui.modules.main.signup.emailcheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.data.api.services.emailcheck.EmailCheckVIew
import com.doublejj.edit.data.models.emailcheck.EmailCheckResponse
import com.doublejj.edit.databinding.ActivityEmailCheckBinding

class EmailCheckActivity : AppCompatActivity(),EmailCheckVIew {

    private lateinit var mBinding: ActivityEmailCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_email_check)



    }

    override fun onPostEmailCheckSuccess(response: EmailCheckResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostEmailCheckFailure(message: String) {
        TODO("Not yet implemented")
    }
}