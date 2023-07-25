package com.erayucar.smsreadermaster.data.di

import android.app.Application
import com.erayucar.smsreadermaster.data.remote.roomdb.MessageDao
import com.erayucar.smsreadermaster.data.remote.PostMessageAPI
import com.erayucar.smsreadermaster.data.repository.DbRepositoryImp
import com.erayucar.smsreadermaster.data.repository.PostMessageRepositoryImpl
import com.erayucar.smsreadermaster.domain.repository.DbRepository
import com.erayucar.smsreadermaster.domain.repository.PostMessageRepository
import com.erayucar.smsreadermaster.presentation.use_case.IMessageUseCase
import com.erayucar.smsreadermaster.data.use_case.MessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabaseRepository(messageDao: MessageDao): DbRepository {
        return DbRepositoryImp(messageDao)
    }

    @Provides
    @Singleton
    fun provideMessageUseCase(postMessageRepository: PostMessageRepository): IMessageUseCase {
        return MessageUseCase(postMessageRepository)
    }

    @Provides
    @Singleton
    fun provideWeatherAPI(): PostMessageAPI {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://discord.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PostMessageAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideSendSmsRepository(api: PostMessageAPI): PostMessageRepository {
        return PostMessageRepositoryImpl(api = api)
    }

}