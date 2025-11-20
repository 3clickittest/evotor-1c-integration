package com.evotor.integration;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.evotor.integration.services.SyncService;
import com.evotor.integration.utils.Logger;
import com.evotor.integration.utils.PreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Главный экран приложения
 */
public class MainActivity extends AppCompatActivity {

    private TextView syncStatusTextView;
    private TextView lastSyncTextView;
    private TextView logTextView;
    private Button startSyncButton;
    private Button settingsButton;
    private Button clearLogButton;

    private PreferencesHelper preferencesHelper;
    private SyncService syncService;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        preferencesHelper = new PreferencesHelper(this);
        syncService = new SyncService(this);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());

        initViews();
        setupListeners();
        setupLogger();
        updateUI();
    }

    private void initViews() {
        syncStatusTextView = findViewById(R.id.syncStatusTextView);
        lastSyncTextView = findViewById(R.id.lastSyncTextView);
        logTextView = findViewById(R.id.logTextView);
        startSyncButton = findViewById(R.id.startSyncButton);
        settingsButton = findViewById(R.id.settingsButton);
        clearLogButton = findViewById(R.id.clearLogButton);
    }

    private void setupListeners() {
        startSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSync();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        clearLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLog();
            }
        });
    }

    private void setupLogger() {
        Logger.setLogListener(new Logger.LogListener() {
            @Override
            public void onNewLogEntry(Logger.LogEntry entry) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateLogView();
                    }
                });
            }
        });
    }

    private void updateUI() {
        if (!preferencesHelper.hasApiToken()) {
            syncStatusTextView.setText(R.string.no_token_configured);
            syncStatusTextView.setTextColor(getResources().getColor(R.color.orange));
            startSyncButton.setEnabled(false);
            return;
        }

        startSyncButton.setEnabled(true);

        boolean syncEnabled = preferencesHelper.isSyncEnabled();
        if (syncEnabled) {
            syncStatusTextView.setText(R.string.sync_status_running);
            syncStatusTextView.setTextColor(getResources().getColor(R.color.green));
        } else {
            syncStatusTextView.setText(R.string.sync_status_idle);
            syncStatusTextView.setTextColor(getResources().getColor(R.color.gray));
        }

        long lastSyncTime = preferencesHelper.getLastSyncTime();
        if (lastSyncTime > 0) {
            String formattedDate = dateFormat.format(new Date(lastSyncTime));
            lastSyncTextView.setText(formattedDate);
        } else {
            lastSyncTextView.setText(R.string.never_synced);
        }

        updateLogView();
    }

    private void updateLogView() {
        List<Logger.LogEntry> entries = Logger.getLogEntries();
        StringBuilder logText = new StringBuilder();

        for (Logger.LogEntry entry : entries) {
            logText.append(entry.toString()).append("\n");
        }

        logTextView.setText(logText.toString());
    }

    private void startSync() {
        if (!preferencesHelper.hasApiToken()) {
            Toast.makeText(this, R.string.no_token_configured, Toast.LENGTH_SHORT).show();
            openSettings();
            return;
        }

        Logger.i("Запуск синхронизации");
        syncStatusTextView.setText(R.string.sync_status_running);
        syncStatusTextView.setTextColor(getResources().getColor(R.color.green));

        syncService.performSync(new SyncService.SyncCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        preferencesHelper.saveLastSyncTime(System.currentTimeMillis());
                        syncStatusTextView.setText(R.string.sync_status_idle);
                        syncStatusTextView.setTextColor(getResources().getColor(R.color.gray));
                        updateUI();
                        Toast.makeText(MainActivity.this, "Синхронизация завершена", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(final String error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        syncStatusTextView.setText(R.string.sync_status_error);
                        syncStatusTextView.setTextColor(getResources().getColor(R.color.red));
                        Toast.makeText(MainActivity.this, "Ошибка: " + error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void clearLog() {
        Logger.clearLog();
        updateLogView();
        Toast.makeText(this, "Лог очищен", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            openSettings();
            return true;
        } else if (id == R.id.action_refresh) {
            updateUI();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
