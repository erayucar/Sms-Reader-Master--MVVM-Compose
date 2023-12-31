package com.erayucar.smsreadermaster.presentation.viewmodel

import android.content.BroadcastReceiver
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erayucar.smsreadermaster.domain.message.SmsTracker
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import com.erayucar.smsreadermaster.domain.repository.DbRepository
import com.erayucar.smsreadermaster.presentation.message_list.MessageListState
import com.erayucar.smsreadermaster.presentation.use_case.IMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(
    private val smsTracker: SmsTracker,
    private val dbRepository: DbRepository,
    private val messageUseCase: IMessageUseCase,

) : ViewModel(){
    private val _messageState = mutableStateOf(MessageListState())
    val messageState: State<MessageListState> = _messageState

    private val _updateMessage = mutableStateOf(SmsMessageModel())
    val updateMessage: State<SmsMessageModel> = _updateMessage

    init {
        viewModelScope.launch{
             smsTracker.receiveMessage().collect{
                print(it.body)
            }

        }

        loadMessage()
    }


     fun loadMessage() {

        viewModelScope.launch {
      smsTracker.receiveMessage().collect { smsText ->
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
                        val postMessage = MessageModel("---" + smsText.sender + "---" + smsText.body)

                        messageUseCase.postMessage(postMessage)
                    }
                }
            }

        }
    }


    fun insertMessage(sender: String, body: String) {
        viewModelScope.launch {
            _messageState.value = MessageListState(isLoading = true)
            val messageModel = SmsMessageModel(
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

    fun updateMessage(sender: String, body: String, uuid: Int) {
        viewModelScope.launch {
            _messageState.value = MessageListState(isLoading = true)

            dbRepository.updateMessage(sender, body, uuid)



        }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            _messageState.value = MessageListState(isLoading = true)
            _messageState.value = MessageListState(messages = dbRepository.getAllMessages())

        }
    }

    fun getMessage(id: Int) {

        viewModelScope.launch {
            _updateMessage.value = dbRepository.getMessage(id)


        }


    }

}