package com.erayucar.smsreadermaster.presentation.use_case

import android.app.Application
import android.util.Log
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.SendSmsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MessageUseCase @Inject constructor(
    private val repository: SendSmsRepository,
    private val application: Application
) {

    operator suspend fun invoke(message: MessageModel) {
        try {
            val call = repository.sendSms(message)
            call.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
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
