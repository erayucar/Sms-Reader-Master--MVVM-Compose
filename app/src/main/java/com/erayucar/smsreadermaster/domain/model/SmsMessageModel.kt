package com.erayucar.smsreadermaster.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class SmsMessageModel(
    @ColumnInfo(name = "sender")
    val sender: String = "",
    @ColumnInfo(name = "body")
    val body: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}