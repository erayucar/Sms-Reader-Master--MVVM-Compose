package com.erayucar.smsreadermaster.data.repository

import com.erayucar.smsreadermaster.data.remote.SendSmsAPI
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.SendSmsRepository
import javax.inject.Inject

class SendSmsRepositoryImpl @Inject constructor(
    private val api: SendSmsAPI
): SendSmsRepository {

    override suspend fun sendSms(message: MessageModel): MessageModel {
        return api.sendSms(message = message)
    }
}