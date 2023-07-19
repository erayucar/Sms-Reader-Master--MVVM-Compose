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
   // https://discord.com/api/webhooks/1131158714355818546/cDKB6o-3VBmPAv1os9D43EN8fRiEdfYIwtvFaK3kiqKgDeNxj-hlTC7XVtBAzLgO5pjy
}