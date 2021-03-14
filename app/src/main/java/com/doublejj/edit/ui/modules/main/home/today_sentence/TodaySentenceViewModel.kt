package com.doublejj.edit.ui.modules.main.home.today_sentence

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodaySentenceViewModel : ViewModel() {
    var tvSentenceWriter = MutableLiveData<String>()
    var tvSentencePosition = MutableLiveData<String>()
    init {
        tvSentenceWriter.value = "유진"
        tvSentencePosition.value = "멘티님"
    }
}