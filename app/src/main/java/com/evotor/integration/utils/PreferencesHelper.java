package com.evotor.integration.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Класс для работы с настройками приложения
 */
public class PreferencesHelper {
    private static final String PREFS_NAME = "EevotorIntegrationPrefs";
    private static final String KEY_API_TOKEN = "api_token";
    private static final String KEY_LAST_SYNC_TIME = "last_sync_time";
    private static final String KEY_SYNC_ENABLED = "sync_enabled";

    private final SharedPreferences preferences;

    public PreferencesHelper(Context context) {
        this.preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Сохранить токен API
     */
    public void saveApiToken(String token) {
        preferences.edit().putString(KEY_API_TOKEN, token).apply();
    }

    /**
     * Получить токен API
     */
    public String getApiToken() {
        return preferences.getString(KEY_API_TOKEN, "");
    }

    /**
     * Проверить, есть ли сохраненный токен
     */
    public boolean hasApiToken() {
        String token = getApiToken();
        return token != null && !token.isEmpty();
    }

    /**
     * Сохранить время последней синхронизации
     */
    public void saveLastSyncTime(long timestamp) {
        preferences.edit().putLong(KEY_LAST_SYNC_TIME, timestamp).apply();
    }

    /**
     * Получить время последней синхронизации
     */
    public long getLastSyncTime() {
        return preferences.getLong(KEY_LAST_SYNC_TIME, 0);
    }

    /**
     * Установить состояние синхронизации
     */
    public void setSyncEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_SYNC_ENABLED, enabled).apply();
    }

    /**
     * Проверить, включена ли синхронизация
     */
    public boolean isSyncEnabled() {
        return preferences.getBoolean(KEY_SYNC_ENABLED, false);
    }

    /**
     * Очистить все настройки
     */
    public void clear() {
        preferences.edit().clear().apply();
    }
}
