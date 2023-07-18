package com.erayucar.smsreadermaster.data.remote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.erayucar.smsreadermaster.domain.model.MessageModel

@Database(entities = [MessageModel::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao


}