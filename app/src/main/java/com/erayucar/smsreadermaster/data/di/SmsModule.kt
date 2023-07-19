package com.erayucar.smsreadermaster.data.di

import com.erayucar.smsreadermaster.data.message.DefaultSmsTracker
import com.erayucar.smsreadermaster.domain.message.SmsTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SmsModule {

    @Binds
    @Singleton
    abstract fun bindSmsRepository(smsRepository: DefaultSmsTracker): SmsTracker
}