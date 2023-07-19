package com.erayucar.smsreadermaster.presentation.message_list

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.erayucar.smsreadermaster.R
import com.erayucar.smsreadermaster.domain.model.SmsMessageModel
import com.erayucar.smsreadermaster.presentation.Screen
import com.erayucar.smsreadermaster.presentation.viewmodel.SmsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageListScreen(
    viewModel: SmsViewModel = hiltViewModel(),
    navController: NavController,
    application: Application
) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        // Permission granted, perform the desired action
        viewModel.getAllMessages()

    }
    LaunchedEffect(key1 = true, block = {
        requestPermissionLauncher.launch(android.Manifest.permission.RECEIVE_SMS)
    })

    var state = viewModel.messageState.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.messageScreen.route)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }, content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (state.isLoading) {
                    item {
                        CircularProgressIndicator()
                    }
                }
                items(state.messages.size) {
                    MesageListItem(
                        message = state.messages[it],
                        application = application,
                        navController = navController
                    )

                }
            }

        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MesageListItem(
    message: SmsMessageModel,
    viewModel: SmsViewModel = hiltViewModel(),
    application: Application,
    navController: NavController
) {
    var mDisplayMenu = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = { mDisplayMenu.value = !mDisplayMenu.value }) {
            Icon(Icons.Default.MoreVert, "")
        }

        DropdownMenu(
            expanded = mDisplayMenu.value,
            onDismissRequest = { mDisplayMenu.value = false }
        ) {

            // Creating dropdown menu item, on click
            // would create a Toast message
            DropdownMenuItem(onClick = {
                navController.navigate(Screen.updateScreen.route + "/" + message.uuid.toString())

            }, text = { Text(text = "Düzenle") })

            // Creating dropdown menu item, on click
            // would create a Toast message
            DropdownMenuItem(
                onClick = {
                    viewModel.deleteMessage(uuid = message.uuid)
                    Toast.makeText(
                        application.applicationContext,
                        "Mesaj silindi",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                text = { Text(text = "Sil") })
        }


        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = message.sender, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = message.body, fontSize = 16.sp)
        }

    }

}

