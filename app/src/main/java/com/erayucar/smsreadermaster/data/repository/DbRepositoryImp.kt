package com.erayucar.smsreadermaster.data.repository

import com.erayucar.smsreadermaster.data.remote.MessageDao
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import com.erayucar.smsreadermaster.domain.repository.DbRepository
import javax.inject.Inject

class DbRepositoryImp @Inject constructor(
    private val messageDao: MessageDao
) : DbRepository {
    override suspend fun insertMessage(message: SmsMessageModel){
         messageDao.insertMessage(message)
    }

    override suspend fun updateMessage(message: SmsMessageModel) {
        messageDao.updateMessage(message)
    }

    override suspend fun getAllMessages(): List<SmsMessageModel> {
        return messageDao.getAllMessages()
    }

    override suspend fun getMessage(id: Int): SmsMessageModel {
        return messageDao.getMessage(id)
    }

    override suspend fun deleteMessage(id: Int) {
        messageDao.deleteMessage(id)
    }
}