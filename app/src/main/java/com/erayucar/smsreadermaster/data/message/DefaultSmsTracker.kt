package com.erayucar.smsreadermaster.data.message

import android.Manifest
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.provider.Telephony
import android.widget.Toast
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import com.erayucar.smsreadermaster.domain.message.SmsTracker
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

class DefaultSmsTracker @Inject constructor(

    private val application: Application,
) : SmsTracker {
        lateinit var br :BroadcastReceiver
    override suspend fun receiveMessage() = channelFlow {

        val scope = CoroutineScope(SupervisorJob())
        val hasSmsRecieverPermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasSmsRecieverPermission) {
            Toast.makeText(
                application,
                "Please enable SMS permission",
                Toast.LENGTH_SHORT
            ).show()

        }



        br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (p1 != null && Telephony.Sms.Intents.SMS_RECEIVED_ACTION == p1.action) {
                    scope.launch(Dispatchers.IO) {

                        for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                            val text = sms.displayMessageBody
                            val timestamp = Timestamp(sms.timestampMillis)
                            val from = sms.originatingAddress
                            val message = SmsMessageModel(from!!, text)


                            send(message)

                        }


                    }


                }

            }
        }


        registerReceiver(
            application,
            br,
            IntentFilter("android.provider.Telephony.SMS_RECEIVED"),
            RECEIVER_EXPORTED
        )
        awaitClose()
    }

}
