package com.erayucar.smsreadermaster.presentation.use_case

import com.erayucar.smsreadermaster.common.Resource
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.SendSmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MessageUseCase @Inject constructor(
    private val repository: SendSmsRepository
) {

    operator fun invoke(message: MessageModel): Flow<Resource<MessageModel>> = flow {
        try {
            emit(Resource.Loading())
            val messageModel = repository.sendSms(message = message)
            emit(Resource.Success(messageModel))

        }catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "Couldn't reach server. Check your internet connection."))

        }
    }
}