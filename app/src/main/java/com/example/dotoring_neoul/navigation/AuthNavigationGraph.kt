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
import com.example.dotoring_neoul.ui.util.register.MenteeInformation
import com.example.dotoring_neoul.ui.util.register.MentorInformation

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
            route = AuthScreen.Register1.route,
            arguments = listOf(
                    navArgument("isMentor") {
                        type = NavType.BoolType
                        defaultValue = true
                    }
                )
        ) { backStackEntry ->
            FirstRegisterScreen(navController = navController, isMentor = backStackEntry.arguments?.getBoolean("isMentor")?: true)
        }

        composable(
            route = AuthScreen.Register2.route,
            arguments = listOf(
                navArgument("isMentor") {
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ) { backStackEntry ->
            val isMentor = backStackEntry.arguments?.getBoolean("isMentor")?: true

            val mentorInformation = if(isMentor) { navController.previousBackStackEntry?.savedStateHandle?.get<MentorInformation>("mentorInfo") } else {
                null
            }

            val menteeInformation = if(!isMentor) { navController.previousBackStackEntry?.savedStateHandle?.get<MenteeInformation>("menteeInfo") } else {
                null
            }

            SecondRegisterScreen(navController = navController, mentorInformation = mentorInformation, menteeInformation = menteeInformation, isMentor = isMentor)
        }

        composable(route = AuthScreen.Register3.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentorInformation>("mentoInfo")

            if (result != null) {
                ThirdRegisterScreen(navController = navController, mentorInformation = result)
            }
        }

        composable(route = AuthScreen.Register4.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentorInformation>("mentoInfo")

            if (result != null) {
                FourthRegisterScreen(navController = navController, mentorInformation = result)
            }
        }

        composable(route = AuthScreen.Register5.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentorInformation>("mentoInfo")

            if (result != null) {
                FifthRegisterScreen(navController = navController, mentorInformation = result)
            }
        }

        composable(route = AuthScreen.Register6.route) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MentorInformation>("mentoInfo")

            if (result != null) {
                SixthRegisterScreen(navController = navController, mentorInformation = result)
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
    object Register1 : AuthScreen(route = "REGISTER1?isMentor={isMentor}") {
        fun passScreenState(
            isMentor: Boolean = true
        ): String {
            return "REGISTER1?isMentor=$isMentor"
        }
    }
    object Register2 : AuthScreen(route = "REGISTER2?isMentor={isMentor}") {
        fun passScreenState(
            isMentor: Boolean = true
        ): String {
            return "REGISTER2?isMentor=$isMentor"
        }
    }
    object Register3 : AuthScreen(route = "REGISTER3")
    object Register4 : AuthScreen(route = "REGISTER4")
    object Register5 : AuthScreen(route = "REGISTER5")
    object Register6 : AuthScreen(route = "REGISTER6")
}