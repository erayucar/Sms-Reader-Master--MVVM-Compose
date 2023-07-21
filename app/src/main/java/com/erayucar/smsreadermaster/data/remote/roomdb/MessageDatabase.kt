package com.erayucar.smsreadermaster.data.remote.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel

@Database(entities = [SmsMessageModel::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao


}