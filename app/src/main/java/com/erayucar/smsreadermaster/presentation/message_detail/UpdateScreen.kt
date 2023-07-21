package com.erayucar.smsreadermaster.presentation.message_detail

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erayucar.smsreadermaster.presentation.viewmodel.SmsViewModel
import com.erayucar.smsreadermaster.ui.theme.Blue


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

    var showDialog = remember { mutableStateOf(false) }





    Box(Modifier.fillMaxSize()) {

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                label = {
                    Text(
                        color = Blue,
                        text = "Gönderen" + "   " + "(${state.sender})"
                    )
                },
                value = sender.value,
                onValueChange = { sender.value = it })
            Spacer(modifier = Modifier.padding(15.dp))
            TextField(colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White),
                label = {
                    Text(
                        color = Blue,
                        text = "Metin" + "    " + "(${state.body})"
                    )
                },
                value = body.value,
                onValueChange = { body.value = it })
            Spacer(modifier = Modifier.height(15.dp))

            Button(onClick = {
                if (body.value.isNotEmpty() && sender.value.isNotEmpty()) {
                    viewModel.updateMessage(sender.value, body.value, uuid)
                    navController.popBackStack()
                } else {
                    showDialog.value = true
                    //Toast.makeText(application,"Lütfen alanları doldurunuz",Toast.LENGTH_SHORT).show()
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                Text(text = "Güncelle", color = Blue)//Color(0xFF0D1B68))
            }

            if (showDialog.value) {
                AlertDialog(onDismissRequest = { showDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text(text = "Tamam")
                        }
                    },
                    text = {
                        Text(text = "Lütfen alanları doldurunuz")
                    })
            }

        }


    }


}