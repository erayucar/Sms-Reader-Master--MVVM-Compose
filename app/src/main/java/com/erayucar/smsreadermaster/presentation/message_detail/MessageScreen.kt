package com.erayucar.smsreadermaster.presentation.message_detail

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
fun MessageScreen(
    viewModel: SmsViewModel = hiltViewModel(),
    app: Application,
    navController: NavController
) {


    val sender = remember {
        mutableStateOf("")
    }
    val body = remember {
        mutableStateOf("")
    }
    var showDialog = remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextField(label = { Text(color = Blue, text = "Gönderen") }, value = sender.value, onValueChange = {
                sender.value = it
            }, colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White))
            Spacer(modifier = Modifier.padding(15.dp))
            TextField(label = { Text(color = Blue, text = "Mesaj") }, value = body.value, onValueChange = {
                body.value = it
            }, colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White))
            Spacer(modifier = Modifier.padding(15.dp))
            Button(
                onClick = {
                    val isEmpty = sender.value.isEmpty() || body.value.isEmpty()
                    if (!isEmpty) {
                        viewModel.insertMessage(sender.value, body.value)
                        navController.popBackStack()


                    } else {
                        showDialog.value = true
                        //Toast.makeText(application,"Lütfen alanları doldurunuz",Toast.LENGTH_SHORT).show()
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Kaydet", color = Blue)

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

