package com.evotor.integration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evotor.integration.adapters.ProductsAdapter;
import com.evotor.integration.api.models.Order;

/**
 * Экран детализации заказа
 */
public class OrderDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ORDER_ID = "order_id";
    public static final String EXTRA_ORDER_NUMBER = "order_number";
    public static final String EXTRA_ORDER_DATE = "order_date";
    public static final String EXTRA_CUSTOMER_NAME = "customer_name";
    public static final String EXTRA_TOTAL_AMOUNT = "total_amount";

    private Toolbar toolbar;
    private TextView customerNameTextView;
    private TextView totalAmountTextView;
    private RecyclerView productsRecyclerView;
    private Button receiptButton;
    private ProductsAdapter productsAdapter;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        initViews();
        loadOrderData();
        setupToolbar();
        setupRecyclerView();
        setupListeners();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        customerNameTextView = findViewById(R.id.customerNameTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        receiptButton = findViewById(R.id.receiptButton);
    }

    private void loadOrderData() {
        Intent intent = getIntent();

        String orderId = intent.getStringExtra(EXTRA_ORDER_ID);
        String orderNumber = intent.getStringExtra(EXTRA_ORDER_NUMBER);
        String orderDate = intent.getStringExtra(EXTRA_ORDER_DATE);
        String customerName = intent.getStringExtra(EXTRA_CUSTOMER_NAME);
        double totalAmount = intent.getDoubleExtra(EXTRA_TOTAL_AMOUNT, 0.0);

        order = new Order(orderId, orderNumber, orderDate, customerName, totalAmount);

        customerNameTextView.setText(customerName);
        totalAmountTextView.setText(String.format("-%.2f", totalAmount));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.order_details_title));
        }
    }

    private void setupRecyclerView() {
        productsAdapter = new ProductsAdapter();
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsRecyclerView.setAdapter(productsAdapter);

        // TODO: Загрузить товары заказа из базы данных или API
        // productsAdapter.setProducts(order.getItems());
    }

    private void setupListeners() {
        receiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPaymentScreen();
            }
        });
    }

    private void openPaymentScreen() {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_ORDER_ID, order.getId());
        intent.putExtra(PaymentActivity.EXTRA_ORDER_NUMBER, order.getNumber());
        intent.putExtra(PaymentActivity.EXTRA_CUSTOMER_NAME, order.getCustomerName());
        intent.putExtra(PaymentActivity.EXTRA_TOTAL_AMOUNT, order.getAmount());
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
