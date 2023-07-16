package com.projectm.feature_auth.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.projectm.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashPage(
	navController: NavController,
	viewModel: SplashViewModel = hiltViewModel()
) {
	LaunchedEffect(key1 = true) {
		viewModel.eventFlow.collectLatest { event ->
			when (event) {
				is SplashViewModel.UiEvent.Navigate -> {
					navController.navigate(event.destination)
				}
			}
		}
	}

	Box(modifier = Modifier.fillMaxSize()) {
		Image(
			painter = painterResource(id = R.drawable.f0rddt0aiaemge7),
			contentScale = ContentScale.Crop,
			contentDescription = "",
		)
	}
}
