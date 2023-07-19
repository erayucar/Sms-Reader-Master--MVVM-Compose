package com.erayucar.smsreadermaster.presentation.message_list

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    val gradientVertically = Brush.verticalGradient(

        startY = 0.3f,
        colors = listOf(Color(0x657012CE), Color(0xFF000000))
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.messageScreen.route)
            }, containerColor = Color(0xFFB894F0)) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }, content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = gradientVertically),
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
                    Divider(color = Color.LightGray)

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
    val mDisplayMenu = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxSize(),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.9f)

        ) {
            Text(
                text = message.sender,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = message.body,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White
            )
        }
        Box(
            contentAlignment = Alignment.TopEnd
        ) {

            IconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    contentColor = Color.White,
                    containerColor = Color.Transparent
                ),
                onClick = { mDisplayMenu.value = !mDisplayMenu.value }) {
                Icon(Icons.Default.MoreVert, "")
            }

            DropdownMenu(

                expanded = mDisplayMenu.value,
                onDismissRequest = { mDisplayMenu.value = false },
                modifier = Modifier.background(Color.White)
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
        }


    }

}

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 15.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = textColor)
        }


    }

}
