package com.erayucar.smsreadermaster.data.di

import android.app.Application
import com.erayucar.smsreadermaster.data.remote.MessageDao
import com.erayucar.smsreadermaster.data.remote.SendSmsAPI
import com.erayucar.smsreadermaster.data.repository.DbRepositoryImp
import com.erayucar.smsreadermaster.data.repository.SendSmsRepositoryImpl
import com.erayucar.smsreadermaster.domain.repository.DbRepository
import com.erayucar.smsreadermaster.domain.repository.SendSmsRepository
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
    fun provideWeatherAPI(application: Application): SendSmsAPI {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .baseUrl("https://discord.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(SendSmsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideSendSmsRepository(api: SendSmsAPI): SendSmsRepository {
        return SendSmsRepositoryImpl(api = api)
    }
}