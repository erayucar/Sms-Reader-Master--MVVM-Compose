package com.erayucar.smsreadermaster.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erayucar.smsreadermaster.data.message.DefaultSmsTracker
import com.erayucar.smsreadermaster.presentation.message_detail.MessageScreen
import com.erayucar.smsreadermaster.presentation.message_detail.UpdateScreen
import com.erayucar.smsreadermaster.presentation.message_list.MessageListScreen
import com.erayucar.smsreadermaster.ui.theme.SmsReaderMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SmsReaderMasterTheme {
                val gradientVertically = Brush.verticalGradient(

                    startY = 0.3f,
                    colors = listOf(Color(0xFF0D1B68), Color(0xFF389CEC))
                )
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = gradientVertically),
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.messageListScreen.route
                    ) {

                        composable(Screen.messageListScreen.route) {
                            MessageListScreen(
                                navController = navController,
                                application = application,
                            )
                        }
                        composable(Screen.messageScreen.route) {
                            MessageScreen(app = application, navController = navController)
                        }
                        composable(Screen.updateScreen.route + "/{uuid}", arguments = listOf(
                            navArgument("uuid") {
                                type = NavType.IntType
                            }
                        )) {
                            val uuid = remember {
                                it.arguments?.getInt("uuid")
                            }
                            UpdateScreen(
                                navController = navController,
                                application = application,
                                uuid = uuid ?: 0
                            )


                        }

                    }

                }
            }
        }


    }

}
