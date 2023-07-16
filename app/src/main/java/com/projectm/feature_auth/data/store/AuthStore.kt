package com.projectm.feature_auth.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthStore @Inject constructor(
	private val context: Context
) {
	companion object {
		private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
		val SESSION_TOKEN = stringPreferencesKey("session_token")
		val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
	}

	val getSessionToken = context.dataStore.data
		.map { preferences ->
			preferences[SESSION_TOKEN] ?: ""
		}

	suspend fun setSession(sessionToken: String, refreshToken: String) {
		context.dataStore.edit { preferences ->
			preferences[SESSION_TOKEN] = sessionToken
			preferences[REFRESH_TOKEN] = refreshToken
		}
	}
}