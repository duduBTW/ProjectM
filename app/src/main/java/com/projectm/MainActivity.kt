package com.projectm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.projectm.feature_auth.presentation.AuthPage
import com.projectm.feature_auth.presentation.authGraph
import com.projectm.feature_manga.presentation.mangaGraph
import com.projectm.ui.theme.ProjectMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ProjectMTheme {
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					val navController = rememberNavController()

					NavHost(navController = navController, startDestination = AuthPage.GROUP) {
						authGraph(navController)
						mangaGraph(navController)
					}
				}
			}
		}
	}
}