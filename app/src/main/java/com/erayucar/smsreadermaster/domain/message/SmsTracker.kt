package com.erayucar.smsreadermaster.domain.message

import android.content.BroadcastReceiver
import com.erayucar.smsreadermaster.domain.model.MessageModel
import java.util.concurrent.Flow

interface SmsTracker {

    suspend fun receiveMessage(): MessageModel?
}