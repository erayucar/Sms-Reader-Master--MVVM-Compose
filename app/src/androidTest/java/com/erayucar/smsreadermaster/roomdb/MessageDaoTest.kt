package com.erayucar.smsreadermaster.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.erayucar.smsreadermaster.data.remote.roomdb.MessageDao
import com.erayucar.smsreadermaster.data.remote.roomdb.MessageDatabase
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MessageDaoTest {
        @get:Rule
        var instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var dao: MessageDao
    @Inject
    @Named("test_db")
     lateinit var database: MessageDatabase

    @Before
    fun setup() {
        /*
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),MessageDatabase::class.java
        ).allowMainThreadQueries().build() */
        hiltRule.inject()
        dao = database.messageDao()

    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMessageTesting()= runTest {
        val message = SmsMessageModel("AKBANK", "kodunuz: 1234")
        dao.insertMessage(message)
        val allMessages = dao.getAllMessages()
        assert(allMessages.contains(message))
    }
    @Test
    fun updateMessageTesting()= runTest {
        val message = SmsMessageModel("AKBANK", "kodunuz: 1234")
        dao.insertMessage(message)
        val allMessages = dao.getAllMessages()
        assert(allMessages.contains(message))
        dao.updateMessage("AKBANK", "kodunuz: 12", 1)
        val updatedMessage = dao.getMessage(1)
        assertThat(updatedMessage.sender).isEqualTo("AKBANK")
        assertThat(updatedMessage.body).isEqualTo("kodunuz: 12")
    }

    @Test
    fun deleteMessageTesting()= runTest {
        val message = SmsMessageModel("AKBANK", "kodunuz: 1234")
        dao.insertMessage(message)
        val allMessages = dao.getAllMessages()
        assert(allMessages.contains(message))
        dao.deleteMessage(1)
        val deletedMessage = dao.getMessage(1)
        assertThat(deletedMessage).isNull()
    }
    @Test
    fun getAllMessagesTesting()= runTest {
        val message = SmsMessageModel("AKBANK", "kodunuz: 1234")
        dao.insertMessage(message)
        val allMessages = dao.getAllMessages()
        assert(allMessages.contains(message))
    }

}