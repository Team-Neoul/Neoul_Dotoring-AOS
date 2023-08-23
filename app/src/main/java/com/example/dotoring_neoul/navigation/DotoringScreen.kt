package com.example.dotoring_neoul.navigation

import com.example.dotoring.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class BottomNavScreen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val icon: Int
) {
    object Home : BottomNavScreen(
        route = "HOME",
        resourceId = R.string.navigation_home,
        icon = R.drawable.ic_navigation_home
    )
    object Calendar : BottomNavScreen(
        route = "CALENDAR",
        resourceId = R.string.navigation_calendar,
        icon = R.drawable.ic_navigation_calendar
    )
    object Message : BottomNavScreen(
        route = "MESSAGE",
        resourceId = R.string.navigation_message,
        icon = R.drawable.ic_navigation_message
    )
    object Mypage : BottomNavScreen(
        route = "MYPAGE",
        resourceId = R.string.navigation_setting,
        icon = R.drawable.ic_navigation_mypage
    )
}