package com.evotor.integration.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Модель документа из API Эвотор (чек/возврат)
 */
public class Document {
    @SerializedName("uuid")
    private String uuid;

    @SerializedName("type")
    private String type;

    @SerializedName("date")
    private String date;

    @SerializedName("deviceUuid")
    private String deviceUuid;

    @SerializedName("storeUuid")
    private String storeUuid;

    @SerializedName("totalSum")
    private double totalSum;

    @SerializedName("transactions")
    private List<Transaction> transactions;

    public static class Transaction {
        @SerializedName("uuid")
        private String uuid;

        @SerializedName("type")
        private String type;

        @SerializedName("sum")
        private double sum;

        @SerializedName("commodities")
        private List<Commodity> commodities;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public List<Commodity> getCommodities() {
            return commodities;
        }

        public void setCommodities(List<Commodity> commodities) {
            this.commodities = commodities;
        }
    }

    public static class Commodity {
        @SerializedName("uuid")
        private String uuid;

        @SerializedName("name")
        private String name;

        @SerializedName("price")
        private double price;

        @SerializedName("quantity")
        private double quantity;

        @SerializedName("sum")
        private double sum;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
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

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public String getStoreUuid() {
        return storeUuid;
    }

    public void setStoreUuid(String storeUuid) {
        this.storeUuid = storeUuid;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Document{" +
                "uuid='" + uuid + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", totalSum=" + totalSum +
                '}';
    }
}
