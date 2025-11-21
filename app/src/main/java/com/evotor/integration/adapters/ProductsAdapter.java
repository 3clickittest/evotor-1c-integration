package com.evotor.integration.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evotor.integration.R;
import com.evotor.integration.api.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер для списка товаров в заказе
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<Order.OrderItem> products;

    public ProductsAdapter() {
        this.products = new ArrayList<>();
    }

    public void setProducts(List<Order.OrderItem> products) {
        this.products = products != null ? products : new ArrayList<Order.OrderItem>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Order.OrderItem product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView quantityTextView;
        private TextView priceTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            quantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
        }

        public void bind(Order.OrderItem product) {
            nameTextView.setText(product.getName());
            quantityTextView.setText(String.format("%.0f шт", product.getQuantity()));
            priceTextView.setText(String.format("%.2f", product.getPrice() * product.getQuantity()));
        }
    }
}
