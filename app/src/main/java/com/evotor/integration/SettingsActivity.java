package com.evotor.integration;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.evotor.integration.api.EevotorApiClient;
import com.evotor.integration.utils.Logger;
import com.evotor.integration.utils.PreferencesHelper;

/**
 * Экран настроек приложения
 */
public class SettingsActivity extends AppCompatActivity {

    private EditText tokenEditText;
    private Button saveButton;
    private Button testConnectionButton;
    private TextView statusTextView;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.settings_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferencesHelper = new PreferencesHelper(this);

        initViews();
        loadSavedToken();
        setupListeners();
    }

    private void initViews() {
        tokenEditText = findViewById(R.id.tokenEditText);
        saveButton = findViewById(R.id.saveButton);
        testConnectionButton = findViewById(R.id.testConnectionButton);
        statusTextView = findViewById(R.id.statusTextView);
    }

    private void loadSavedToken() {
        String savedToken = preferencesHelper.getApiToken();
        if (savedToken != null && !savedToken.isEmpty()) {
            tokenEditText.setText(savedToken);
        }
    }

    private void setupListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToken();
            }
        });

        testConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConnection();
            }
        });
    }

    private void saveToken() {
        String token = tokenEditText.getText().toString().trim();

        if (token.isEmpty()) {
            Toast.makeText(this, R.string.empty_token_error, Toast.LENGTH_SHORT).show();
            return;
        }

        preferencesHelper.saveApiToken(token);
        Toast.makeText(this, R.string.token_saved, Toast.LENGTH_SHORT).show();
        Logger.i("Токен API сохранен");

        showStatus(getString(R.string.token_saved), true);
    }

    private void testConnection() {
        String token = tokenEditText.getText().toString().trim();

        if (token.isEmpty()) {
            Toast.makeText(this, R.string.empty_token_error, Toast.LENGTH_SHORT).show();
            return;
        }

        showStatus("Проверка соединения...", false);
        testConnectionButton.setEnabled(false);

        EevotorApiClient apiClient = new EevotorApiClient(token);
        apiClient.testConnection(new EevotorApiClient.ApiCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                testConnectionButton.setEnabled(true);
                if (result) {
                    showStatus(getString(R.string.connection_success), true);
                    Toast.makeText(SettingsActivity.this, R.string.connection_success, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                testConnectionButton.setEnabled(true);
                String message = getString(R.string.connection_failed, error);
                showStatus(message, false);
                Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showStatus(String message, boolean success) {
        statusTextView.setText(message);
        statusTextView.setVisibility(View.VISIBLE);
        statusTextView.setTextColor(getResources().getColor(success ? R.color.green : R.color.red));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
