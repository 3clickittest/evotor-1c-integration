package com.evotor.integration.services;

import android.content.Context;

import com.evotor.integration.api.EevotorApiClient;
import com.evotor.integration.api.models.Document;
import com.evotor.integration.api.models.Product;
import com.evotor.integration.utils.Logger;
import com.evotor.integration.utils.PreferencesHelper;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Сервис для синхронизации данных с API Облака Эвотор
 */
public class SyncService {

    private final Context context;
    private final PreferencesHelper preferencesHelper;
    private EevotorApiClient apiClient;

    /**
     * Интерфейс callback для синхронизации
     */
    public interface SyncCallback {
        void onSuccess();
        void onError(String error);
    }

    public SyncService(Context context) {
        this.context = context;
        this.preferencesHelper = new PreferencesHelper(context);
    }

    /**
     * Выполнить синхронизацию документов и товаров
     */
    public void performSync(final SyncCallback callback) {
        String token = preferencesHelper.getApiToken();

        if (token == null || token.isEmpty()) {
            Logger.e("Токен API не настроен");
            callback.onError("Токен API не настроен");
            return;
        }

        apiClient = new EevotorApiClient(token);
        Logger.i("Начало синхронизации");

        // Для демонстрации используем тестовый UUID магазина
        // В реальном приложении нужно получить список магазинов и выбрать нужный
        final String testStoreUuid = "20170804-0000-5000-8000-100000000001";

        final AtomicInteger completedTasks = new AtomicInteger(0);
        final AtomicInteger totalTasks = 2;
        final StringBuilder errors = new StringBuilder();

        // Синхронизация документов
        syncDocuments(testStoreUuid, new SyncCallback() {
            @Override
            public void onSuccess() {
                checkCompletion(completedTasks, totalTasks, errors, callback);
            }

            @Override
            public void onError(String error) {
                errors.append("Документы: ").append(error).append("; ");
                checkCompletion(completedTasks, totalTasks, errors, callback);
            }
        });

        // Синхронизация товаров
        syncProducts(testStoreUuid, new SyncCallback() {
            @Override
            public void onSuccess() {
                checkCompletion(completedTasks, totalTasks, errors, callback);
            }

            @Override
            public void onError(String error) {
                errors.append("Товары: ").append(error).append("; ");
                checkCompletion(completedTasks, totalTasks, errors, callback);
            }
        });
    }

    /**
     * Синхронизировать документы
     */
    private void syncDocuments(String storeUuid, final SyncCallback callback) {
        Logger.i("Синхронизация документов для магазина: " + storeUuid);

        apiClient.getDocuments(storeUuid, new EevotorApiClient.ApiCallback<List<Document>>() {
            @Override
            public void onSuccess(List<Document> documents) {
                if (documents != null && !documents.isEmpty()) {
                    Logger.i("Получено документов: " + documents.size());

                    for (Document doc : documents) {
                        Logger.d("Документ: " + doc.getType() + ", UUID: " + doc.getUuid() +
                                ", Сумма: " + doc.getTotalSum());
                    }

                    // Здесь должна быть логика сохранения документов в локальную БД
                    // и отправка в 1С через их API

                    callback.onSuccess();
                } else {
                    Logger.i("Документов не найдено");
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(String error) {
                Logger.e("Ошибка при синхронизации документов: " + error);
                callback.onError(error);
            }
        });
    }

    /**
     * Синхронизировать товары
     */
    private void syncProducts(String storeUuid, final SyncCallback callback) {
        Logger.i("Синхронизация товаров для магазина: " + storeUuid);

        apiClient.getProducts(storeUuid, new EevotorApiClient.ApiCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> products) {
                if (products != null && !products.isEmpty()) {
                    Logger.i("Получено товаров: " + products.size());

                    for (Product product : products) {
                        Logger.d("Товар: " + product.getName() + ", UUID: " + product.getUuid() +
                                ", Цена: " + product.getPrice());
                    }

                    // Здесь должна быть логика сохранения товаров в локальную БД
                    // и отправка в 1С через их API

                    callback.onSuccess();
                } else {
                    Logger.i("Товаров не найдено");
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(String error) {
                Logger.e("Ошибка при синхронизации товаров: " + error);
                callback.onError(error);
            }
        });
    }

    /**
     * Проверить завершение всех задач синхронизации
     */
    private void checkCompletion(AtomicInteger completedTasks, int totalTasks,
                                 StringBuilder errors, SyncCallback callback) {
        int completed = completedTasks.incrementAndGet();

        if (completed >= totalTasks) {
            if (errors.length() > 0) {
                Logger.e("Синхронизация завершена с ошибками: " + errors.toString());
                callback.onError(errors.toString());
            } else {
                Logger.i("Синхронизация успешно завершена");
                callback.onSuccess();
            }

            if (apiClient != null) {
                apiClient.shutdown();
            }
        }
    }
}
