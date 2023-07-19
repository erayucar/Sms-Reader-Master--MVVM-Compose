package com.erayucar.smsreadermaster.domain.message

import com.erayucar.smsreadermaster.domain.model.SmsMessageModel

interface SmsTracker {

    suspend fun receiveMessage(): SmsMessageModel?
}