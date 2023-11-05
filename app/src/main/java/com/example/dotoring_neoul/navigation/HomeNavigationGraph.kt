package com.example.dotoring_neoul.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dotoring_neoul.ui.home.MainScreen
import com.example.dotoring_neoul.ui.message.messageBox.MessageBoxScreen
import com.example.dotoring_neoul.ui.message.messageDetail.MessageDetailScreen
import com.example.dotoring_neoul.ui.detail.MemberDetailInformation
import com.example.dotoring_neoul.ui.detail.MemberDetailedScreen

/**
 * 로그인 이후 화면 네비게이션 그래프
 */
@Composable
fun HomeNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomNavScreen.Home.route
    ) {
        composable(route = BottomNavScreen.Home.route) {
            MainScreen(navController = navController)
        }

        composable(route = BottomNavScreen.Matching.route) {

        }
        composable(route = BottomNavScreen.Message.route) {
            MessageBoxScreen(navController = navController)
        }

        composable(route = BottomNavScreen.Mypage.route) {
        }

        mentiDetailNavGraph(navController)

        messageDetailNavGraph(navController)

    }
}

/**
 * 멘티 디테일 화면 네비게이션 그래프
 */
fun NavGraphBuilder.mentiDetailNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.MENTI_DETAILS,
        startDestination = MentiDetailScreen.MentiDetailed.route
    ) {
        composable(route = MentiDetailScreen.MentiDetailed.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MemberDetailInformation>("menteeDetail")

            if(result != null) {
                MemberDetailedScreen(memberDetailInformation = result)
            }
        }
    }
}

/**
 * 메세지 디테일 네비게이션 그래프
 */
fun NavGraphBuilder.messageDetailNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.MESSAGE_DETAILS,
        startDestination = MessageDetailScreen.MessageDetailed.route
    ) {
//        composable(route = MessageDetailScreen.MessageDetailed.route) {
//            val result =remember {
//                navController.previousBackStackEntry!!.savedStateHandle.get<MessageBox>("roomPK")
//            }
//            Log.d("메세지", "메시지 실행:")
//            if (result != null) {
//                val roomInfo=result.roomPK
//                MessageDetailScreen(navController = navController, roomPk = roomInfo)
//            }
    }
    composable(route = MessageDetailScreen.MessageDetailed.route){
        MessageDetailScreen(navController = navController)
        Log.d("메세지","메세지")

    }
}


/**
 * 메세지 디테일 화면 오브젝트
 */
sealed class MessageDetailScreen(val route: String) {
    object MessageDetailed: MessageDetailScreen(route = "MESSAGE_DETAILED" )
}

/**
 * 멘티 디테일 화면 오브젝트
 */
sealed class MentiDetailScreen(val route: String) {
    object MentiDetailed: MentiDetailScreen(route = "MENTI_DETAILED")
}