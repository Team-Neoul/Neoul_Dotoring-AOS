package com.example.dotoring_neoul.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.dotoring_neoul.ui.login.LoginScreen
import com.example.dotoring_neoul.ui.register.branch.RegisterBranchScreen
import com.example.dotoring_neoul.ui.register.fifth.FifthRegisterScreen
import com.example.dotoring_neoul.ui.register.first.FirstRegisterScreen
import com.example.dotoring_neoul.ui.register.fourth.FourthRegisterScreen
import com.example.dotoring_neoul.ui.register.second.SecondRegisterScreen
import com.example.dotoring_neoul.ui.register.sixth.SixthRegisterScreen
import com.example.dotoring_neoul.ui.register.third.ThirdRegisterScreen
import com.example.dotoring_neoul.ui.util.register.MentoInformation

/**
 * 로그인 및 회원가입 화면 네비게이션 빌더
 */
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = AuthScreen.Waiting.route) {
            /* TODO */
        }

        composable(route = AuthScreen.Branch.route) {
            RegisterBranchScreen(navController = navController)
        }

        composable(
            route = "AuthScreen/Register1/route/{isMentor}",
            arguments = listOf(navArgument("isMentor") { type = NavType.BoolType })
        ) { backStackEntry ->
            FirstRegisterScreen(navController = navController, isMentor = backStackEntry.arguments?.getBoolean("isMentor")?: true)
        }

        composable(route = AuthScreen.Register2.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentoInformation>("mentoInfo")

            if (result != null) {
                SecondRegisterScreen(navController = navController, mentoInformation = result)
            }
        }

        composable(route = AuthScreen.Register3.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentoInformation>("mentoInfo")

            if (result != null) {
                ThirdRegisterScreen(navController = navController, mentoInformation = result)
            }
        }

        composable(route = AuthScreen.Register4.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentoInformation>("mentoInfo")

            if (result != null) {
                FourthRegisterScreen(navController = navController, mentoInformation = result)
            }
        }

        composable(route = AuthScreen.Register5.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentoInformation>("mentoInfo")

            if (result != null) {
                FifthRegisterScreen(navController = navController, mentoInformation = result)
            }
        }

        composable(route = AuthScreen.Register6.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentoInformation>("mentoInfo")

            if (result != null) {
                SixthRegisterScreen(navController = navController, mentoInformation = result)
            }
        }
    }
}

/**
 * 네비게이션 가능한 로그인 및 회원가입 화면 정의
 */
sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object Waiting : AuthScreen(route = "WAITING")
    object Branch : AuthScreen(route="BRANCH")
    object Register1 : AuthScreen(route = "REGISTER1")
    object Register2 : AuthScreen(route = "REGISTER2")
    object Register3 : AuthScreen(route = "REGISTER3")
    object Register4 : AuthScreen(route = "REGISTER4")
    object Register5 : AuthScreen(route = "REGISTER5")
    object Register6 : AuthScreen(route = "REGISTER6")
}