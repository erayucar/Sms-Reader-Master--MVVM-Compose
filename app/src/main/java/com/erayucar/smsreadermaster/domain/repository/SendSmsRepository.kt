package com.erayucar.smsreadermaster.domain.repository

import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel

interface SendSmsRepository {
    suspend fun sendSms(message: MessageModel): MessageModel
}