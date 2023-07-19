package com.erayucar.smsreadermaster.data.repository

import com.erayucar.smsreadermaster.data.remote.SendSmsAPI
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.SendSmsRepository
import retrofit2.Call
import javax.inject.Inject

class SendSmsRepositoryImpl @Inject constructor(
    private val api: SendSmsAPI
) : SendSmsRepository {

    override suspend fun sendSms(message: MessageModel): Call<Unit> {
        return api.sendSms(message = message)
    }
}