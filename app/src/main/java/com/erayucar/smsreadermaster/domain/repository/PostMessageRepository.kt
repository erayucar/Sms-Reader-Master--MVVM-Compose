package com.erayucar.smsreadermaster.domain.repository

import com.erayucar.smsreadermaster.domain.model.MessageModel
import retrofit2.Call

interface PostMessageRepository {
    suspend fun postMessage(message: MessageModel): Call<Unit>


}