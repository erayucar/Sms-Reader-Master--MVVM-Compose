package com.erayucar.smsreadermaster.data.repository

import com.erayucar.smsreadermaster.data.remote.PostMessageAPI
import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.PostMessageRepository
import retrofit2.Call
import javax.inject.Inject

class PostMessageRepositoryImpl @Inject constructor(
    private val api: PostMessageAPI
) : PostMessageRepository {

    override suspend fun postMessage(message: MessageModel): Call<Unit> {
        return api.postMessage(message = message)
    }
}