package com.evotor.integration.api;

import android.os.Handler;
import android.os.Looper;

import com.evotor.integration.api.models.ApiResponse;
import com.evotor.integration.api.models.Document;
import com.evotor.integration.api.models.Product;
import com.evotor.integration.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Клиент для работы с API Облака Эвотор
 */
public class EevotorApiClient {
    private static final String BASE_URL = "https://api.evotor.ru/api/v1";
    private static final int TIMEOUT_SECONDS = 30;

    private final OkHttpClient httpClient;
    private final Gson gson;
    private final String apiToken;
    private final ExecutorService executor;
    private final Handler mainHandler;

    /**
     * Интерфейс callback для асинхронных запросов
     */
    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }

    public EevotorApiClient(String apiToken) {
        this.apiToken = apiToken;
        this.httpClient = new OkHttpClient.Builder()
                .build();
        this.gson = new Gson();
        this.executor = Executors.newFixedThreadPool(3);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Проверить соединение с API
     */
    public void testConnection(final ApiCallback<Boolean> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/stores")
                            .addHeader("Authorization", "Bearer " + apiToken)
                            .build();

                    Response response = httpClient.newCall(request).execute();
                    final boolean success = response.isSuccessful();
                    response.close();

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (success) {
                                Logger.i("Соединение с API Эвотор установлено");
                                callback.onSuccess(true);
                            } else {
                                Logger.e("Ошибка соединения с API");
                                callback.onError("Ошибка авторизации");
                            }
                        }
                    });
                } catch (final Exception e) {
                    Logger.e("Ошибка при проверке соединения", e);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    /**
     * Получить список документов
     */
    public void getDocuments(final String storeUuid, final ApiCallback<List<Document>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = BASE_URL + "/inventories/stores/" + storeUuid + "/documents";
                    Logger.d("Запрос документов: " + url);

                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", "Bearer " + apiToken)
                            .build();

                    Response response = httpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        Logger.d("Ответ получен: " + responseBody.length() + " байт");

                        Type type = new TypeToken<ApiResponse<Document>>(){}.getType();
                        ApiResponse<Document> apiResponse = gson.fromJson(responseBody, type);

                        if (apiResponse.hasError()) {
                            final String error = apiResponse.getError();
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Logger.e("Ошибка API: " + error);
                                    callback.onError(error);
                                }
                            });
                        } else {
                            final List<Document> documents = apiResponse.getItems();
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Logger.i("Получено документов: " + (documents != null ? documents.size() : 0));
                                    callback.onSuccess(documents);
                                }
                            });
                        }
                    } else {
                        final String errorMsg = "HTTP " + response.code();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Logger.e("Ошибка HTTP: " + errorMsg);
                                callback.onError(errorMsg);
                            }
                        });
                    }
                    response.close();
                } catch (final Exception e) {
                    Logger.e("Ошибка при получении документов", e);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    /**
     * Получить список товаров
     */
    public void getProducts(final String storeUuid, final ApiCallback<List<Product>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = BASE_URL + "/inventories/stores/" + storeUuid + "/products";
                    Logger.d("Запрос товаров: " + url);

                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", "Bearer " + apiToken)
                            .build();

                    Response response = httpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        Logger.d("Ответ получен: " + responseBody.length() + " байт");

                        Type type = new TypeToken<ApiResponse<Product>>(){}.getType();
                        ApiResponse<Product> apiResponse = gson.fromJson(responseBody, type);

                        if (apiResponse.hasError()) {
                            final String error = apiResponse.getError();
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Logger.e("Ошибка API: " + error);
                                    callback.onError(error);
                                }
                            });
                        } else {
                            final List<Product> products = apiResponse.getItems();
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Logger.i("Получено товаров: " + (products != null ? products.size() : 0));
                                    callback.onSuccess(products);
                                }
                            });
                        }
                    } else {
                        final String errorMsg = "HTTP " + response.code();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Logger.e("Ошибка HTTP: " + errorMsg);
                                callback.onError(errorMsg);
                            }
                        });
                    }
                    response.close();
                } catch (final Exception e) {
                    Logger.e("Ошибка при получении товаров", e);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e.getMessage());
                        }
                    });
                }
            }
        });
    }

    /**
     * Освободить ресурсы
     */
    public void shutdown() {
        executor.shutdown();
    }
}
