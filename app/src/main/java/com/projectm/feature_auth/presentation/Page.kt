package com.projectm.feature_auth.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.projectm.feature_auth.presentation.login.LoginPage
import com.projectm.feature_auth.presentation.splash.SplashPage

fun NavGraphBuilder.authGraph(navController: NavController) {
	navigation(startDestination = AuthPage.SplashPage.route, route = AuthPage.GROUP) {
		composable(AuthPage.LoginPage.route) {
			LoginPage(navController)
		}
		composable(AuthPage.SplashPage.route) {
			SplashPage(navController)
		}
	}
}

sealed class AuthPage(val route: String) {
	object LoginPage: AuthPage("login")
	object SplashPage: AuthPage("splash")

	companion object {
		const val GROUP = "auth"
	}
}

