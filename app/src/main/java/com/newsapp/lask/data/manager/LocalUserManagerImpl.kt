package com.newsapp.lask.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.newsapp.lask.data.utils.Constants
import com.newsapp.lask.data.utils.Constants.USER_SETTINGS
import com.newsapp.lask.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImpl(
    private val context: Context,
) : LocalUserManager {

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferencesKey.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKey.APP_ENTRY] ?: false
        }
    }

    override suspend fun saveLoginAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_LOGIN] = true
        }
    }

    override fun readLoginAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_LOGIN] ?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

private object PreferencesKey {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}

private object PreferencesKeys {
    val APP_LOGIN = booleanPreferencesKey(name = Constants.APP_LOGIN)
}