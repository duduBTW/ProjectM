package com.projectm.feature_auth.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
	@POST("auth/login")
	suspend fun login(
		@Body request: LoginRequest
	): LoginResponse
}

data class LoginRequest(
	val username: String,
	val password: String,
)

data class LoginResponse(
	val result: String,
	val token: LoginToken,
	val errors: List<LoginError>
)

data class LoginToken(
	val session: String,
	val refresh: String
)

data class LoginError(
	val id: String,
	val status: Int,
	val title: String,
	val detail: String?,
	val context: String?,
)