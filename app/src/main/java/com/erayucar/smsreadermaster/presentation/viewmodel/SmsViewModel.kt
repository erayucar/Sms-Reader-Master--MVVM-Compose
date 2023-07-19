package com.erayucar.smsreadermaster.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erayucar.smsreadermaster.domain.message.SmsTracker
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.DbRepository
import com.erayucar.smsreadermaster.presentation.message_list.MessageListState
import com.erayucar.smsreadermaster.presentation.use_case.MessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(
    private val smsTracker: SmsTracker,
    private val dbRepository: DbRepository,
    private val messageUseCase: MessageUseCase

) : ViewModel() {

    private val _messageState = mutableStateOf(MessageListState())
    val messageState: State<MessageListState> = _messageState

    private val _updateMessage = mutableStateOf(MessageModel())
    val updateMessage: State<MessageModel> = _updateMessage

    init {
        loadMessage()
    }


    fun loadMessage() {
        viewModelScope.launch {

            smsTracker.receiveMessage()?.let { smsText ->
                dbRepository.getAllMessages().forEach() { value ->

                    val isFound = smsText.body.contains(
                        value.body,
                        ignoreCase = true
                    ) || smsText.body == value.body
                    val isSender = smsText.sender.contains(
                        value.sender,
                        ignoreCase = true
                    ) || smsText.sender == value.sender
                    if (isFound && isSender) {
                        // post request with retrofit
                        val postMessage = MessageModel(
                            sender = smsText.sender,
                            body = smsText.body,
                        )
                        println(postMessage.body)
                        println(postMessage.sender)
                        /* messageUseCase(postMessage).onEach {
                        when (it) {
                            is Resource.Success -> {
                            }
                            is Resource.Error -> {
                                _messageState.value = MessageListState(error = it.message!!)
                            }
                            is Resource.Loading -> {
                            }
                        }
                    }.launchIn(viewModelScope)*/


                    } else {

                    }
                }
            }
            loadMessage()

        }
    }


    fun insertMessage(sender: String, body: String) {
        viewModelScope.launch {
            _messageState.value = MessageListState(isLoading = true)
            val messageModel = MessageModel(
                sender = sender,
                body = body
            )
            dbRepository.insertMessage(messageModel)
        }
    }

    fun deleteMessage(uuid: Int) {
        viewModelScope.launch {
            _messageState.value = MessageListState(isLoading = true)
            dbRepository.deleteMessage(uuid)
            getAllMessages()
        }
    }

    fun updateMessage(messageModel: MessageModel) {
        viewModelScope.launch {
            _messageState.value = MessageListState(isLoading = true)

            dbRepository.updateMessage(messageModel)

        }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            _messageState.value = MessageListState(messages = dbRepository.getAllMessages())

        }
    }

    fun getMessage(id: Int) {

        viewModelScope.launch {
            _updateMessage.value = dbRepository.getMessage(id)


        }


    }
}