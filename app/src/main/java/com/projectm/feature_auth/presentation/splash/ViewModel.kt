package com.projectm.feature_auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectm.feature_auth.data.store.AuthStore
import com.projectm.feature_auth.presentation.AuthPage
import com.projectm.feature_manga.presentation.MangaPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule


@HiltViewModel
class SplashViewModel @Inject constructor(
	private val authStore: AuthStore,
): ViewModel() {
	private val _eventFlow = MutableSharedFlow<UiEvent>()
	val eventFlow = _eventFlow.asSharedFlow()

	init {
		Timer().schedule(2500) {
			authStore.getSessionToken.onEach { sessionToken ->
				_eventFlow.emit(
					UiEvent.Navigate(
						if (sessionToken.isBlank()) {
							AuthPage.LoginPage.route
						} else {
							MangaPage.FollowedFeedPage.route
						}
					)
				)
			}.launchIn(viewModelScope)
		}
	}

	sealed class UiEvent {
		data class Navigate(val destination: String) : UiEvent()
	}
}