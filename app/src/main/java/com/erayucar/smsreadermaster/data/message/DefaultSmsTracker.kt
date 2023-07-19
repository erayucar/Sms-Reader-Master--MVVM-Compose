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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import com.erayucar.smsreadermaster.domain.message.SmsTracker
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.sql.Timestamp
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class DefaultSmsTracker @Inject constructor(
    private val application: Application,
) : SmsTracker {
    override suspend fun receiveMessage(): SmsMessageModel? {
        val hasSmsRecieverPermission = ContextCompat.checkSelfPermission(
            application, Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasSmsRecieverPermission) {
            Toast.makeText(
                application.applicationContext,
                "Please give permission",
                Toast.LENGTH_SHORT
            ).show()

        }


        return suspendCancellableCoroutine { cont ->
            val br = object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    if (p1 != null && Telephony.Sms.Intents.SMS_RECEIVED_ACTION == p1.action) {
                        for (sms in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                            val text = sms.displayMessageBody
                            val timestamp = Timestamp(sms.timestampMillis)
                            val from = sms.originatingAddress
                            val message = SmsMessageModel(from!!, text)
                            p0?.unregisterReceiver(this)
                            cont.resume(message)

                        }
                    } else {
                        // Handle other intents
                        cont.resume(null)
                    }


                }

            }


            registerReceiver(
                application,
                br,
                IntentFilter("android.provider.Telephony.SMS_RECEIVED"),
                RECEIVER_EXPORTED
            )
        }
    }

}
