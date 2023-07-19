package com.erayucar.smsreadermaster.domain.repository

import com.erayucar.smsreadermaster.domain.model.MessageModel
import retrofit2.Call

interface SendSmsRepository {
    suspend fun sendSms(message: MessageModel): Call<Unit>
}