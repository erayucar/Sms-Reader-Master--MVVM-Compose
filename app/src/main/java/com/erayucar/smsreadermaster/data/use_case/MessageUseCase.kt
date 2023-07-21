package com.erayucar.smsreadermaster.data.use_case

import android.util.Log
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.PostMessageRepository
import com.erayucar.smsreadermaster.presentation.use_case.IMessageUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MessageUseCase @Inject constructor(
    private val repository: PostMessageRepository,
) : IMessageUseCase {

    override suspend fun postMessage(message: MessageModel) {
        try {
            val call = repository.postMessage(message)
            call.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Log.e("TAG", "onResponse: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {}

            })
        } catch (e: Exception) {
            Log.e("TAG", e.localizedMessage!!)
        }
    }
}


/* Flow<Resource<MessageModel>> = flow {
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

 */
