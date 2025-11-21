package com.evotor.integration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Экран пробития чека с оплатой
 */
public class PaymentActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_ID = "order_id";
    public static final String EXTRA_ORDER_NUMBER = "order_number";
    public static final String EXTRA_CUSTOMER_NAME = "customer_name";
    public static final String EXTRA_TOTAL_AMOUNT = "total_amount";

    private Toolbar toolbar;
    private TextView phoneNumberTextView;
    private Switch emailSwitch;
    private TextView totalAmountTextView;
    private Button cashPaymentButton;
    private Button cardPaymentButton;
    private Button paymentTypesButton;

    private String orderId;
    private String orderNumber;
    private String customerName;
    private double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initViews();
        loadOrderData();
        setupToolbar();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        emailSwitch = findViewById(R.id.emailSwitch);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        cashPaymentButton = findViewById(R.id.cashPaymentButton);
        cardPaymentButton = findViewById(R.id.cardPaymentButton);
        paymentTypesButton = findViewById(R.id.paymentTypesButton);
    }

    private void loadOrderData() {
        Intent intent = getIntent();

        orderId = intent.getStringExtra(EXTRA_ORDER_ID);
        orderNumber = intent.getStringExtra(EXTRA_ORDER_NUMBER);
        customerName = intent.getStringExtra(EXTRA_CUSTOMER_NAME);
        totalAmount = intent.getDoubleExtra(EXTRA_TOTAL_AMOUNT, 0.0);

        totalAmountTextView.setText(String.format("%.0f ₽", totalAmount));

        // TODO: Загрузить номер телефона клиента
        // phoneNumberTextView.setText(phoneNumber);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.payment_title));
        }
    }

    private void setupListeners() {
        cashPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCashPayment();
            }
        });

        cardPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processCardPayment();
            }
        });

        paymentTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentTypes();
            }
        });
    }

    private void processCashPayment() {
        // TODO: Интеграция с Evotor SDK для пробития чека наличными
        Toast.makeText(this, "Оплата наличными: " + String.format("%.2f ₽", totalAmount), Toast.LENGTH_SHORT).show();

        // Пример кода для интеграции с Evotor:
        // try {
        //     ReceiptApi.openReceipt(this, ReceiptApi.RECEIPT_TYPE_SELL);
        //     // Добавить товары и провести оплату
        // } catch (Exception e) {
        //     Logger.e("Ошибка пробития чека", e);
        // }
    }

    private void processCardPayment() {
        // TODO: Интеграция с Evotor SDK для пробития чека картой
        Toast.makeText(this, "Оплата картой: " + String.format("%.2f ₽", totalAmount), Toast.LENGTH_SHORT).show();

        // Пример кода для интеграции с Evotor:
        // try {
        //     ReceiptApi.openReceipt(this, ReceiptApi.RECEIPT_TYPE_SELL);
        //     // Добавить товары и провести оплату картой
        // } catch (Exception e) {
        //     Logger.e("Ошибка пробития чека", e);
        // }
    }

    private void showPaymentTypes() {
        // TODO: Показать диалог с дополнительными типами оплаты
        Toast.makeText(this, "Дополнительные типы оплаты", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
