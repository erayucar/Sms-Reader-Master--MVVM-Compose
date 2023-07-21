package com.erayucar.smsreadermaster.repository

import com.erayucar.smsreadermaster.domain.model.MessageModel
import com.erayucar.smsreadermaster.domain.repository.PostMessageRepository
import okhttp3.Request
import okio.Timeout
import retrofit2.Call

class FakePostMessageRepo: PostMessageRepository {
    override suspend fun postMessage(message: MessageModel): Call<Unit> {
        return object : Call<Unit> {
            override fun enqueue(callback: retrofit2.Callback<Unit>) {
                callback.onResponse(this, retrofit2.Response.success(Unit))
            }

            override fun isExecuted(): Boolean {
                return true
            }

            override fun clone(): Call<Unit> {
                return this
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun request(): Request {
                return Request.Builder().build()
            }

            override fun timeout(): Timeout {
                return Timeout()
            }

            override fun cancel() {
            }

            override fun execute(): retrofit2.Response<Unit> {
                return retrofit2.Response.success(Unit)
            }
    }
}

}