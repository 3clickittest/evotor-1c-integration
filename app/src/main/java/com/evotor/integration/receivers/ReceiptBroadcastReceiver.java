package com.evotor.integration.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.evotor.integration.utils.Logger;

/**
 * BroadcastReceiver для получения событий о продажах с терминала Эвотор
 */
public class ReceiptBroadcastReceiver extends BroadcastReceiver {

    // Actions для событий Эвотор
    private static final String ACTION_RECEIPT_SELL = "ru.evotor.devices.events.ReceiptEvent.SELL_RECEIPT";
    private static final String ACTION_RECEIPT_PAYBACK = "ru.evotor.devices.events.ReceiptEvent.PAYBACK_RECEIPT";
    private static final String ACTION_RECEIPT_BUY = "ru.evotor.devices.events.ReceiptEvent.BUY_RECEIPT";
    private static final String ACTION_RECEIPT_BUYBACK = "ru.evotor.devices.events.ReceiptEvent.BUYBACK_RECEIPT";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }

        String action = intent.getAction();
        Logger.i("Получено событие: " + action);

        Bundle extras = intent.getExtras();
        if (extras != null) {
            logExtras(extras);
        }

        switch (action) {
            case ACTION_RECEIPT_SELL:
                handleSellReceipt(context, intent);
                break;

            case ACTION_RECEIPT_PAYBACK:
                handlePaybackReceipt(context, intent);
                break;

            case ACTION_RECEIPT_BUY:
                handleBuyReceipt(context, intent);
                break;

            case ACTION_RECEIPT_BUYBACK:
                handleBuybackReceipt(context, intent);
                break;

            default:
                Logger.w("Неизвестное событие: " + action);
                break;
        }
    }

    /**
     * Обработка продажи
     */
    private void handleSellReceipt(Context context, Intent intent) {
        Logger.i("Обработка чека продажи");

        try {
            // Получение данных из intent
            String receiptUuid = intent.getStringExtra("receiptUuid");
            String deviceUuid = intent.getStringExtra("deviceUuid");

            if (receiptUuid != null) {
                Logger.i("UUID чека: " + receiptUuid);
            }

            if (deviceUuid != null) {
                Logger.i("UUID устройства: " + deviceUuid);
            }

            // Здесь должна быть логика:
            // 1. Получить детали чека через Integration Library
            // 2. Сохранить в локальную БД
            // 3. Отправить в 1С через API Облака

        } catch (Exception e) {
            Logger.e("Ошибка при обработке чека продажи", e);
        }
    }

    /**
     * Обработка возврата
     */
    private void handlePaybackReceipt(Context context, Intent intent) {
        Logger.i("Обработка чека возврата");

        try {
            String receiptUuid = intent.getStringExtra("receiptUuid");
            String deviceUuid = intent.getStringExtra("deviceUuid");

            if (receiptUuid != null) {
                Logger.i("UUID чека возврата: " + receiptUuid);
            }

            if (deviceUuid != null) {
                Logger.i("UUID устройства: " + deviceUuid);
            }

            // Здесь должна быть логика обработки возврата

        } catch (Exception e) {
            Logger.e("Ошибка при обработке чека возврата", e);
        }
    }

    /**
     * Обработка прихода
     */
    private void handleBuyReceipt(Context context, Intent intent) {
        Logger.i("Обработка чека прихода");

        try {
            String receiptUuid = intent.getStringExtra("receiptUuid");

            if (receiptUuid != null) {
                Logger.i("UUID чека прихода: " + receiptUuid);
            }

            // Здесь должна быть логика обработки прихода

        } catch (Exception e) {
            Logger.e("Ошибка при обработке чека прихода", e);
        }
    }

    /**
     * Обработка возврата прихода
     */
    private void handleBuybackReceipt(Context context, Intent intent) {
        Logger.i("Обработка чека возврата прихода");

        try {
            String receiptUuid = intent.getStringExtra("receiptUuid");

            if (receiptUuid != null) {
                Logger.i("UUID чека возврата прихода: " + receiptUuid);
            }

            // Здесь должна быть логика обработки возврата прихода

        } catch (Exception e) {
            Logger.e("Ошибка при обработке чека возврата прихода", e);
        }
    }

    /**
     * Логирование всех extras из intent
     */
    private void logExtras(Bundle extras) {
        Logger.d("Extras в intent:");
        for (String key : extras.keySet()) {
            Object value = extras.get(key);
            Logger.d("  " + key + " = " + (value != null ? value.toString() : "null"));
        }
    }
}
