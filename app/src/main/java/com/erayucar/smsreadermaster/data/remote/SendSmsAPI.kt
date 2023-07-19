package com.erayucar.smsreadermaster.data.remote

import com.erayucar.smsreadermaster.domain.model.MessageModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SendSmsAPI {

    @POST("/api/webhooks/1131158714355818546/cDKB6o-3VBmPAv1os9D43EN8fRiEdfYIwtvFaK3kiqKgDeNxj-hlTC7XVtBAzLgO5pjy")
    suspend fun sendSms(
        @Body message: MessageModel
    ): Call<Unit>
    // https://discord.com/api/webhooks/1131158714355818546/cDKB6o-3VBmPAv1os9D43EN8fRiEdfYIwtvFaK3kiqKgDeNxj-hlTC7XVtBAzLgO5pjy
}