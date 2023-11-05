package com.example.dotoring_neoul.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dotoring_neoul.navigation.bottom_navigation_bar.DotoringScreenNavigationBar

/**
 * 전체 네비게이션 그래프 정의
 */
@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            DotoringScreenNavigationBar()
        }
    }
}

/**
 * 전체 네비게이션 그래프 오브젝트 정의
 */
object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val MENTI_DETAILS = "menti_details_graph"
    const val MESSAGE_DETAILS = "message_detail_graph"
}