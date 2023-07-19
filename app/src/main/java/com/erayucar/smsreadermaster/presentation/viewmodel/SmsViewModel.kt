package com.erayucar.smsreadermaster.presentation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erayucar.smsreadermaster.common.Resource
import com.erayucar.smsreadermaster.domain.message.SmsTracker
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import com.erayucar.smsreadermaster.domain.repository.DbRepository
import com.erayucar.smsreadermaster.presentation.message_list.MessageListState
import com.erayucar.smsreadermaster.presentation.use_case.MessageUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(
    private val smsTracker: SmsTracker,
    private val dbRepository: DbRepository,
    private val messageUseCase: MessageUseCase,
    private val application: Application

) : ViewModel() {

    private val _messageState = mutableStateOf(MessageListState())
    val messageState: State<MessageListState> = _messageState

    private val _updateMessage = mutableStateOf(SmsMessageModel())
    val updateMessage: State<SmsMessageModel> = _updateMessage

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
                        val postMessage = MessageModel("**" + smsText.sender + "**" + smsText.body)



                         messageUseCase(postMessage).onEach {
                        when (it) {
                            is Resource.Success -> {
                                Toast.makeText(application.applicationContext, "Mesaj gönderildi", Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Error -> {
                                _messageState.value = MessageListState(error = it.message!!)
                            }
                            is Resource.Loading -> {
                            }
                        }
                    }.launchIn(viewModelScope)


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

    fun updateMessage(messageModel: SmsMessageModel) {
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