package com.evotor.integration;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evotor.integration.adapters.OrdersAdapter;
import com.evotor.integration.adapters.ReceiptsAdapter;
import com.evotor.integration.api.models.Document;
import com.evotor.integration.api.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Главный экран приложения с разделами Заказы и Чеки
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView ordersRecyclerView;
    private RecyclerView receiptsRecyclerView;

    private OrdersAdapter ordersAdapter;
    private ReceiptsAdapter receiptsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupToolbar();
        setupRecyclerViews();
        loadData();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        receiptsRecyclerView = findViewById(R.id.receiptsRecyclerView);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }
    }

    private void setupRecyclerViews() {
        // Настройка адаптера заказов
        ordersAdapter = new OrdersAdapter(new OrdersAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                openOrderDetails(order);
            }
        });
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ordersRecyclerView.setAdapter(ordersAdapter);

        // Настройка адаптера чеков
        receiptsAdapter = new ReceiptsAdapter(new ReceiptsAdapter.OnReceiptClickListener() {
            @Override
            public void onReceiptClick(Document receipt) {
                openReceiptDetails(receipt);
            }
        });
        receiptsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        receiptsRecyclerView.setAdapter(receiptsAdapter);
    }

    private void loadData() {
        // TODO: Загрузить заказы из 1С или локальной базы данных
        List<Order> orders = createTestOrders();
        ordersAdapter.setOrders(orders);

        // TODO: Загрузить чеки из Evotor Cloud API
        List<Document> receipts = new ArrayList<>();
        receiptsAdapter.setReceipts(receipts);
    }

    /**
     * Создает тестовые заказы для проверки интерфейса
     * TODO: Удалить после реализации загрузки реальных данных
     */
    private List<Order> createTestOrders() {
        List<Order> orders = new ArrayList<>();

        Order order1 = new Order(
                "1",
                "0000-00000057",
                "16.11.2025 15:57",
                "Белошапкин Андрей",
                1000.00
        );

        Order order2 = new Order(
                "2",
                "0000-00000058",
                "16.11.2025 16:30",
                "Иванов Иван",
                500.00
        );

        Order order3 = new Order(
                "3",
                "0000-00000059",
                "16.11.2025 17:15",
                "Петров Петр",
                750.00
        );

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        return orders;
    }

    private void openOrderDetails(Order order) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra(OrderDetailsActivity.EXTRA_ORDER_ID, order.getId());
        intent.putExtra(OrderDetailsActivity.EXTRA_ORDER_NUMBER, order.getNumber());
        intent.putExtra(OrderDetailsActivity.EXTRA_ORDER_DATE, order.getDate());
        intent.putExtra(OrderDetailsActivity.EXTRA_CUSTOMER_NAME, order.getCustomerName());
        intent.putExtra(OrderDetailsActivity.EXTRA_TOTAL_AMOUNT, order.getAmount());
        startActivity(intent);
    }

    private void openReceiptDetails(Document receipt) {
        // TODO: Реализовать открытие детализации чека
        // Intent intent = new Intent(this, ReceiptDetailsActivity.class);
        // intent.putExtra("receipt_id", receipt.getUuid());
        // startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Обновить данные при возврате на экран
        loadData();
    }
}
