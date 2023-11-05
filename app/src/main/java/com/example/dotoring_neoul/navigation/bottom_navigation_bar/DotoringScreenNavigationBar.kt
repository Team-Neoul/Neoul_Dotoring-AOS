package com.example.dotoring_neoul.navigation.bottom_navigation_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dotoring_neoul.navigation.BottomNavScreen
import com.example.dotoring_neoul.navigation.HomeNavGraph

/**
 * 홈 화면 Bottom Navigation Bar Composable
 */
@Composable
fun DotoringScreenNavigationBar(
    navController: NavHostController = rememberNavController()
) {
    Scaffold (
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        HomeNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}

/**
 * Bottom Navigation Sheet Composable
 */
@Composable
private fun BottomBar(
    navController: NavHostController
) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Calendar,
        BottomNavScreen.Message,
        BottomNavScreen.Mypage
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = Color.White
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

/**
 * 홈화면 Bottom Navigation Bar Composable
 */
@Composable
private fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = stringResource(screen.resourceId))
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        selectedContentColor = Color(0xff8BD045),
        unselectedContentColor = Color(0xffC3C3C3),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}