package com.scholar.domain.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow


private const val USER_PREFERENCES = "user_preference"
val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

interface DataStorePreference {
    companion object {
        val isUserLoggedIn = booleanPreferencesKey(name = "is_user_logged_in")
        val isAppFirstOpen = booleanPreferencesKey(name = "app_first_open")
        val languageId = intPreferencesKey(name = "id_language")
        val userId = stringPreferencesKey(name = "user_id")
    }

    suspend fun <T> saveValue(key: Preferences.Key<T>, value: T)
    fun <T> readValue(key: Preferences.Key<T>): Flow<T?>
}