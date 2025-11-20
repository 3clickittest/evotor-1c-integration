package com.evotor.integration.api.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Обертка для ответа API
 */
public class ApiResponse<T> {
    @SerializedName("items")
    private List<T> items;

    @SerializedName("paging")
    private Paging paging;

    @SerializedName("error")
    private String error;

    public static class Paging {
        @SerializedName("offset")
        private int offset;

        @SerializedName("count")
        private int count;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null && !error.isEmpty();
    }
}
