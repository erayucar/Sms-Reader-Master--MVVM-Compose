package com.erayucar.smsreadermaster.data.remote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Message
import android.provider.Telephony
import java.security.Permissions
import java.sql.Timestamp

class MessageService {
    fun receiveMessage(): BroadcastReceiver {
        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == p1?.action) {
                    for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(p1)) {
                        val timestamp = Timestamp(smsMessage.timestampMillis)
                        val from = smsMessage.originatingAddress
                        val body = smsMessage.messageBody
                        print(body)
                    }
                }
            }
        }

        return br
    }

}
