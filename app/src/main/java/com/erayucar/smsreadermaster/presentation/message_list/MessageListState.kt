package com.erayucar.smsreadermaster.presentation.message_list

import com.erayucar.smsreadermaster.domain.model.MessageModel

data class MessageListState(
    val isLoading: Boolean = false,
    val messages: List<MessageModel> = emptyList(),
    val error: String = ""
)