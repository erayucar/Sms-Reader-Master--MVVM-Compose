package com.erayucar.smsreadermaster.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.erayucar.smsreadermaster.domain.model.MessageModel


@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessage(vararg message: MessageModel)

    @Update
    suspend fun updateMessage(message: MessageModel)

    @Query("SELECT * FROM message")
    suspend fun getAllMessages(): List<MessageModel>

    @Query("SELECT * FROM message WHERE uuid = :id")
    suspend fun getMessage(id: Int): MessageModel

    @Query("DELETE FROM message WHERE uuid = :id")
    suspend fun deleteMessage(id: Int)


}