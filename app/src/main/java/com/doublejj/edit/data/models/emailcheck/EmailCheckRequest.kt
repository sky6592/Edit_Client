package com.doublejj.edit.data.models.emailcheck

import com.google.gson.annotations.SerializedName

data class EmailCheckRequest (
    @SerializedName("email") val email: String
)