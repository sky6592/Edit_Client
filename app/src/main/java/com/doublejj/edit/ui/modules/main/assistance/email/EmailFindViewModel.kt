package com.doublejj.edit.ui.modules.main.assistance.email

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doublejj.edit.data.models.emailfind.EmailFindResponse

class EmailFindViewModel : ViewModel() {
    val email: MutableLiveData<EmailFindResponse> by lazy {
        MutableLiveData<EmailFindResponse>()
    }
}