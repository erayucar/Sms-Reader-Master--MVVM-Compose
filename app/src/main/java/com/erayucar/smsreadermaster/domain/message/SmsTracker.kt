package com.erayucar.smsreadermaster.domain.message

import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.internal.ChannelFlow
import javax.inject.Singleton

@Singleton
interface SmsTracker {

    suspend fun receiveMessage(): Flow<SmsMessageModel>
}