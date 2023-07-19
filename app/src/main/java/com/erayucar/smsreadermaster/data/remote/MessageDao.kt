package com.erayucar.smsreadermaster.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel


@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessage(vararg message: SmsMessageModel)

    @Query("UPDATE message SET sender = :sender,body = :body WHERE uuid = :uuid")
    suspend fun updateMessage(sender: String, body: String, uuid: Int)

    @Query("SELECT * FROM message")
    suspend fun getAllMessages(): List<SmsMessageModel>

    @Query("SELECT * FROM message WHERE uuid = :id")
    suspend fun getMessage(id: Int): SmsMessageModel

    @Query("DELETE FROM message WHERE uuid = :id")
    suspend fun deleteMessage(id: Int)


}