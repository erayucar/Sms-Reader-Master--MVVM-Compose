package com.erayucar.smsreadermaster.presentation.use_case

import com.erayucar.smsreadermaster.domain.model.MessageModel

interface IMessageUseCase {

    suspend fun postMessage(message: MessageModel)
}