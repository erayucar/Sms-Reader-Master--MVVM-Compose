package com.erayucar.smsreadermaster.domain.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class MessageModel (
    @SerializedName("content")
    val message: String? = null
        )