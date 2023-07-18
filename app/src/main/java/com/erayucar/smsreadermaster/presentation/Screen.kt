package com.erayucar.smsreadermaster.presentation

sealed class Screen(val route: String) {
    object messageListScreen : Screen("message_list_screen")

    object messageScreen : Screen("message_detail_screen")

    object updateScreen : Screen("update_screen")

}
