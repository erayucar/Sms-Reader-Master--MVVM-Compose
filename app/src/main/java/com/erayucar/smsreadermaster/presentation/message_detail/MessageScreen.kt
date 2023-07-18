package com.erayucar.smsreadermaster.presentation.message_detail

import android.app.Application
import android.widget.EditText
import android.widget.Space
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erayucar.smsreadermaster.presentation.viewmodel.SmsViewModel

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

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TextField(label = { Text(text = "Gönderen")}, value = sender.value, onValueChange ={
                sender.value = it
            })
            Spacer(modifier = Modifier.padding(15.dp))
            TextField(label = { Text(text = "Mesaj")}, value = body.value, onValueChange ={
                body.value = it
            })

            Button(onClick = {
                val isEmpty = sender.value.isEmpty() || body.value.isEmpty()
                if (!isEmpty){
                    viewModel.insertMessage(sender.value, body.value)
                    navController.popBackStack()


                }else{
                    Toast.makeText(app.applicationContext, "Alanlar Boş Olamaz!", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Kaydet")
            }


        }
    }


}

