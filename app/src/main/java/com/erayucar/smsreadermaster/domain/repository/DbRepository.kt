package com.erayucar.smsreadermaster.domain.repository

import com.erayucar.smsreadermaster.domain.model.SmsMessageModel

interface DbRepository {
    suspend fun insertMessage(message: SmsMessageModel)
    suspend fun updateMessage(message: SmsMessageModel)
    suspend fun getAllMessages(): List<SmsMessageModel>
    suspend fun getMessage(id: Int): SmsMessageModel
    suspend fun deleteMessage(id: Int)
}