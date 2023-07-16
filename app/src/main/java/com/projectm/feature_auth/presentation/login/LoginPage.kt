package com.projectm.feature_auth.presentation.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginPage(
	navController: NavController,
	viewModel: LoginViewModel = hiltViewModel()
) {
	val status = viewModel.status

	AnimatedContent(
		targetState = status.value != null,
		transitionSpec = {
			slideInHorizontally { width -> if(!targetState) width else -width } with
					slideOutHorizontally { width -> if(!targetState) -width else width }
		}
	) { isLoading ->
		if(isLoading) {
			LoginStatus(
				status = status.value
			)
		} else {
			LoginForm(
				onSubmit = {
					viewModel.logIn()
				}
			)
		}
	}
}


sealed class AuthStatus(val message: String? = null) {
	class Error(message: String?): AuthStatus(message)
	class Ok: AuthStatus()
	class Loading: AuthStatus()
}

@Composable
fun LoginStatus(
	status: AuthStatus? = null,
	modifier: Modifier = Modifier
) {
	if(status != null) Box(
		modifier = modifier.fillMaxSize().padding(20.dp),
		contentAlignment = Alignment.Center
	) {
		Text(
			when(status) {
				is AuthStatus.Loading -> "Authenticating..."
				is AuthStatus.Error -> status.message ?: "Something went wrong!"
				is AuthStatus.Ok -> "Authenticated with success!"
			}
		)

		Row(
			modifier = Modifier.align(Alignment.BottomEnd)
		) {
			// Icon()
			Text("Redirecting")
		}
	}
}

@Composable
fun LoginForm(
	onSubmit: () -> Unit
) {
	Column(
		modifier =
		Modifier
			.fillMaxSize()
			.padding(top = 32.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
	) {
		Text("Login")
		Spacer(modifier = Modifier.weight(1.0f))
		LoginInput(
			label = "Username"
		)
		LoginInput(
			label = "Password",
		)
		Spacer(modifier = Modifier.weight(1.0f))
		Button(
			modifier = Modifier.fillMaxWidth(),
			onClick = onSubmit
		) {
			Text("Enter")
		}
	}
}

@Composable
fun LoginInput(
	label: String,
	modifier: Modifier = Modifier
) {
	Box(
		modifier =
			modifier.padding(top = 36.dp)
	) {
		Text(label)
		BasicTextField(
			value = "",
			onValueChange = {
				//
			},
		)
	}
}