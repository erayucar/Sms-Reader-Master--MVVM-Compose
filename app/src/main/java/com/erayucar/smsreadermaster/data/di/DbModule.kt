package com.erayucar.smsreadermaster.data.di

import android.content.Context
import androidx.room.Room
import com.erayucar.smsreadermaster.data.remote.MessageDatabase
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MessageDatabase::class.java, "message_database"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: MessageDatabase) = db.messageDao()

    @Provides
    fun provideEntity() = SmsMessageModel()
}
