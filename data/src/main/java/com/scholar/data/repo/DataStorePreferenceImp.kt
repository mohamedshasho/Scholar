package com.scholar.data.repo

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.scholar.domain.repo.DataStorePreference
import com.scholar.domain.repo.datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStorePreferenceImp(context: Context) : DataStorePreference {
    private val datastore = context.datastore

    override suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        datastore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun <T> readValue(key: Preferences.Key<T>): Flow<T?> {
        return datastore.data
            .map { preferences ->
                preferences[key]
            }
    }


}