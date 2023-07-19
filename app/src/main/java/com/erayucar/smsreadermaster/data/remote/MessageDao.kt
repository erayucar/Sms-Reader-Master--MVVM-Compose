package com.erayucar.smsreadermaster.data.remote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel


@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessage(vararg message: SmsMessageModel)

    @Update
    suspend fun updateMessage(message: SmsMessageModel)

    @Query("SELECT * FROM message")
    suspend fun getAllMessages(): List<SmsMessageModel>

    @Query("SELECT * FROM message WHERE uuid = :id")
    suspend fun getMessage(id: Int): SmsMessageModel

    @Query("DELETE FROM message WHERE uuid = :id")
    suspend fun deleteMessage(id: Int)


}