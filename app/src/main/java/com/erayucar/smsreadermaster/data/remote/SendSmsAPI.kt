package com.erayucar.smsreadermaster.data.remote

import com.erayucar.smsreadermaster.domain.model.MessageModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SendSmsAPI {

    @POST("/channels/1130834360287903845/1130834367686651977")
    suspend fun sendSms(
        @Body message: MessageModel
    ): MessageModel
    //https://discord.com/channels/1130834360287903845/1130834367686651977
}