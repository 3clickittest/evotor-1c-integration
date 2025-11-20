package com.evotor.integration.utils;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Класс для логирования событий в приложении
 */
public class Logger {
    private static final String TAG = "EevotorIntegration";
    private static final int MAX_LOG_ENTRIES = 100;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());

    private static final List<LogEntry> logEntries = new ArrayList<LogEntry>();
    private static LogListener logListener;

    /**
     * Класс для хранения записи лога
     */
    public static class LogEntry {
        public final String timestamp;
        public final String level;
        public final String message;

        public LogEntry(String timestamp, String level, String message) {
            this.timestamp = timestamp;
            this.level = level;
            this.message = message;
        }

        @Override
        public String toString() {
            return timestamp + " [" + level + "] " + message;
        }
    }

    /**
     * Интерфейс для уведомления о новых записях в логе
     */
    public interface LogListener {
        void onNewLogEntry(LogEntry entry);
    }

    /**
     * Установить слушателя логов
     */
    public static void setLogListener(LogListener listener) {
        logListener = listener;
    }

    /**
     * Добавить запись в лог
     */
    private static void addLogEntry(String level, String message) {
        String timestamp = dateFormat.format(new Date());
        LogEntry entry = new LogEntry(timestamp, level, message);

        synchronized (logEntries) {
            logEntries.add(entry);
            if (logEntries.size() > MAX_LOG_ENTRIES) {
                logEntries.remove(0);
            }
        }

        if (logListener != null) {
            logListener.onNewLogEntry(entry);
        }
    }

    /**
     * Логирование информационного сообщения
     */
    public static void i(String message) {
        Log.i(TAG, message);
        addLogEntry("INFO", message);
    }

    /**
     * Логирование сообщения об ошибке
     */
    public static void e(String message) {
        Log.e(TAG, message);
        addLogEntry("ERROR", message);
    }

    /**
     * Логирование сообщения об ошибке с исключением
     */
    public static void e(String message, Throwable throwable) {
        Log.e(TAG, message, throwable);
        addLogEntry("ERROR", message + ": " + throwable.getMessage());
    }

    /**
     * Логирование отладочного сообщения
     */
    public static void d(String message) {
        Log.d(TAG, message);
        addLogEntry("DEBUG", message);
    }

    /**
     * Логирование предупреждения
     */
    public static void w(String message) {
        Log.w(TAG, message);
        addLogEntry("WARNING", message);
    }

    /**
     * Получить все записи лога
     */
    public static List<LogEntry> getLogEntries() {
        synchronized (logEntries) {
            return new ArrayList<LogEntry>(logEntries);
        }
    }

    /**
     * Очистить лог
     */
    public static void clearLog() {
        synchronized (logEntries) {
            logEntries.clear();
        }
    }
}
