package com.evotor.integration.api.models;

import java.util.List;

/**
 * Модель заказа
 */
public class Order {
    private String id;
    private String number;
    private String date;
    private String customerName;
    private double amount;
    private List<OrderItem> items;

    public Order() {
    }

    public Order(String id, String number, String date, String customerName, double amount) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.customerName = customerName;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * Элемент заказа (товар)
     */
    public static class OrderItem {
        private String name;
        private double price;
        private double quantity;

        public OrderItem(String name, double price, double quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }
    }
}
