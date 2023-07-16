package com.projectm.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectm.feature_auth.data.remote.AuthApi
import com.projectm.feature_auth.data.remote.LoginRequest
import com.projectm.feature_auth.data.store.AuthStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.util.Timer
import javax.inject.Inject
import kotlin.concurrent.schedule

@HiltViewModel
class LoginViewModel @Inject constructor(
	private val authStore: AuthStore,
	private val authApi: AuthApi
): ViewModel() {
	private val _status = mutableStateOf<AuthStatus?>(null)
	val status: State<AuthStatus?> = _status

	private fun setStatus(newStatus: AuthStatus) {
		_status.value = newStatus

		if(_status.value is AuthStatus.Error) {
			return
		}

		Timer().schedule(2000) {
			_status.value = null
		}
	}

	fun logIn() {
		viewModelScope.launch {
			try {
				setStatus(AuthStatus.Loading())

				val res = authApi.login(
					LoginRequest(
						username = "username",
						password = "password"
					)
				)

				authStore.setSession(
					sessionToken = res.token.session,
					refreshToken = res.token.refresh
				)
				setStatus(AuthStatus.Ok())
			} catch (e: HttpException) {
				val error =
					JSONObject(e.response()?.errorBody()?.string() ?: "")
						.getJSONArray("errors")
						.getJSONObject(0)

				setStatus(
					AuthStatus.Error(
						if(error.isNull("detail")) {
							null
						} else {
							error.getString("detail")
						}
					)
				)
			} catch (e: Exception) {
				setStatus(AuthStatus.Error("Something went wrong!"))
			}
		}
	}
}