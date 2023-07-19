package com.erayucar.smsreadermaster.presentation.message_detail

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import com.erayucar.smsreadermaster.presentation.viewmodel.SmsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    viewModel: SmsViewModel = hiltViewModel(),
    navController: NavController,
    application: Application,
    uuid: Int
) {
    val sender = remember {
        mutableStateOf("")
    }
    val body = remember {
        mutableStateOf("")
    }

    val state = viewModel.updateMessage.value
   LaunchedEffect(key1 = true, block = {

       viewModel.getMessage(uuid)

   })





    Box(Modifier.fillMaxSize()) {

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                label = { Text(text = "Gönderen" +"   "+"(${state.sender})")},
                value = sender.value,
                onValueChange = { sender.value = it })
            Spacer(modifier = Modifier.padding(15.dp))
            TextField(
                label = { Text(text = "Metin" +"    "+ "(${state.body})") },
                value = body.value,
                onValueChange = { body.value = it })
            Button(onClick = {
                if (body.value.isNotEmpty() && sender.value.isNotEmpty()) {
                    val updateMessage = SmsMessageModel(sender = sender.value, body = body.value)
                    viewModel.updateMessage(updateMessage)
                    navController.popBackStack()
                }
            }) {
                Text(text = "Güncelle")
            }

        }


    }


}