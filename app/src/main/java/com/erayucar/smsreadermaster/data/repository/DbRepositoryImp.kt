package com.erayucar.smsreadermaster.data.repository

import com.erayucar.smsreadermaster.data.remote.MessageDao
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.DbRepository
import javax.inject.Inject

class DbRepositoryImp @Inject constructor(
    private val messageDao: MessageDao
) : DbRepository {
    override suspend fun insertMessage(message: MessageModel){
         messageDao.insertMessage(message)
    }

    override suspend fun updateMessage(message: MessageModel) {
        messageDao.updateMessage(message)
    }

    override suspend fun getAllMessages(): List<MessageModel> {
        return messageDao.getAllMessages()
    }

    override suspend fun getMessage(id: Int): MessageModel {
        return messageDao.getMessage(id)
    }

    override suspend fun deleteMessage(id: Int) {
        messageDao.deleteMessage(id)
    }
}