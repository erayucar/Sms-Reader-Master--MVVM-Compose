package com.erayucar.smsreadermaster.domain.repository

import com.erayucar.smsreadermaster.domain.model.MessageModel

interface DbRepository {
    suspend fun insertMessage(message: MessageModel)
    suspend fun updateMessage(message: MessageModel)
    suspend fun getAllMessages(): List<MessageModel>
    suspend fun getMessage(id: Int): MessageModel
    suspend fun deleteMessage(id: Int)
}