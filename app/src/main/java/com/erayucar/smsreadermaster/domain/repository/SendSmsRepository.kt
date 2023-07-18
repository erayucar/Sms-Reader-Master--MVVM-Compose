package com.erayucar.smsreadermaster.domain.repository

import com.erayucar.smsreadermaster.domain.model.MessageModel

interface SendSmsRepository {
    suspend fun sendSms(message: MessageModel): MessageModel
}