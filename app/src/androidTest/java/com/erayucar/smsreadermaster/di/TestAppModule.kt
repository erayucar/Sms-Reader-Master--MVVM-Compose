package com.erayucar.smsreadermaster.di

import android.content.Context
import androidx.room.Room
import com.erayucar.smsreadermaster.data.remote.roomdb.MessageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {


    @Provides
    @Named("test_db")
    fun injectInMemoryRoom(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(
        context, MessageDatabase::class.java
    ).allowMainThreadQueries().build()

}