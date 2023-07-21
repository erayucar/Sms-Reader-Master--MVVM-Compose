package com.erayucar.smsreadermaster.repository

import androidx.lifecycle.MutableLiveData
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import com.erayucar.smsreadermaster.domain.repository.DbRepository

class FakeDbRepository: DbRepository {

    private val messageList = mutableListOf<SmsMessageModel>()
    private val mesageLiveData = MutableLiveData<List<SmsMessageModel>>(messageList)
    override suspend fun insertMessage(message: SmsMessageModel) {
        messageList.add(message)
    }

    override suspend fun updateMessage(sender: String, body: String, uuid: Int) {
        val message = SmsMessageModel(sender = sender, body = body)
        messageList.find { it.uuid == uuid }?.let {
            messageList.remove(it)
            messageList.add(message)

    }
    }

    override suspend fun getAllMessages(): List<SmsMessageModel> {
        return mesageLiveData.value ?: emptyList()
    }

    override suspend fun getMessage(id: Int): SmsMessageModel {
        return messageList.first { it.uuid == id }
    }

    override suspend fun deleteMessage(id: Int) {
        messageList.remove(getMessage(id))
    }
}