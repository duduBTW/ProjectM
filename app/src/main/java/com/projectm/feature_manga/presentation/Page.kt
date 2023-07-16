package com.projectm.feature_manga.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.projectm.feature_manga.presentation.followed_feed.FollowedFeedPage

fun NavGraphBuilder.mangaGraph(navController: NavController) {
	navigation(startDestination = MangaPage.FollowedFeedPage.route, route = MangaPage.GROUP) {
		composable(MangaPage.FollowedFeedPage.route) {
			FollowedFeedPage(navController)
		}
	}
}

sealed class MangaPage(val route: String) {
	object FollowedFeedPage: MangaPage("followed_feed")

	companion object {
		const val GROUP = "manga"
	}
}

