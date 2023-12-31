package com.example.dotoring.navigation

import com.example.dotoring.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * 네비게이션바 아이콘 등 지정
 */
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
    object Matching : BottomNavScreen(
        route = "MATCHING",
        resourceId = R.string.navigation_matching,
        icon = R.drawable.ic_navigation_matching
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