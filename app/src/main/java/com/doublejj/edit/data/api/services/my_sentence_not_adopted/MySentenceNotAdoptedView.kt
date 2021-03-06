package com.doublejj.edit.data.api.services.my_sentence_not_adopted

import com.doublejj.edit.data.models.my_sentence_not_adopted.MySentenceNotAdoptedResponse

interface MySentenceNotAdoptedView {
    fun onGetMySentenceNotAdoptedSuccess(response: MySentenceNotAdoptedResponse)
    fun onGetMySentenceNotAdoptedFailure(message: String)
}