package com.erayucar.smsreadermaster.presentation.message_list

import com.erayucar.smsreadermaster.domain.model.SmsMessageModel

data class MessageListState(
    val isLoading: Boolean = false,
    val messages: List<SmsMessageModel> = emptyList(),
    val error: String = ""
)