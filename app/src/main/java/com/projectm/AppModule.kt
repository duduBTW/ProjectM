package com.projectm

import android.content.Context
import com.projectm.core.data.MangadexApi
import com.projectm.feature_auth.data.remote.AuthApi
import com.projectm.feature_auth.data.store.AuthStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Provides
	@Singleton
	fun provideAuthStore(
		@ApplicationContext context: Context
	): AuthStore = AuthStore(context)

	@Provides
	@Singleton
	fun provideAuthApi(
		authStore: AuthStore
	): AuthApi {
		val httpClient = OkHttpClient.Builder()
		val httpLogger = generateHttpLogger()
		val httpAuthInterceptor = generateHttpAuthInterceptor(
			authStore
		)

		// Add log interceptor
		httpClient.addInterceptor(httpAuthInterceptor)
		httpClient.addInterceptor(httpLogger)

		return Retrofit.Builder()
			.baseUrl(MangadexApi.BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create())
			.client(httpClient.build())
			.build()
			.create()
	}
}

fun generateHttpAuthInterceptor(authStore: AuthStore): Interceptor {
	var sessionToken = ""

	// Listen for changes on the token
	authStore.getSessionToken.onEach { newToken ->
		sessionToken = newToken
	}.launchIn(CoroutineScope(CoroutineName("auth")))

	return Interceptor { chain ->
		val newRequest: Request = chain.request().newBuilder()
			.addHeader("Authorization", "Bearer $sessionToken")
			.build()

		chain.proceed(newRequest)
	}
}

fun generateHttpLogger(): Interceptor {
	val logging = HttpLoggingInterceptor()
	logging.setLevel(HttpLoggingInterceptor.Level.BODY)

	return logging
}