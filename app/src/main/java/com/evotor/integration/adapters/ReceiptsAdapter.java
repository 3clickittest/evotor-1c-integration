package com.evotor.integration.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evotor.integration.R;
import com.evotor.integration.api.models.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер для списка чеков
 */
public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.ReceiptViewHolder> {

    private List<Document> receipts;
    private OnReceiptClickListener listener;

    public interface OnReceiptClickListener {
        void onReceiptClick(Document receipt);
    }

    public ReceiptsAdapter(OnReceiptClickListener listener) {
        this.receipts = new ArrayList<>();
        this.listener = listener;
    }

    public void setReceipts(List<Document> receipts) {
        this.receipts = receipts != null ? receipts : new ArrayList<Document>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_receipt, parent, false);
        return new ReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
        Document receipt = receipts.get(position);
        holder.bind(receipt);
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    class ReceiptViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private TextView customerTextView;
        private TextView amountTextView;

        public ReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.receiptDateTextView);
            customerTextView = itemView.findViewById(R.id.receiptCustomerTextView);
            amountTextView = itemView.findViewById(R.id.receiptAmountTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onReceiptClick(receipts.get(position));
                    }
                }
            });
        }

        public void bind(Document receipt) {
            dateTextView.setText(receipt.getDate());
            customerTextView.setText(receipt.getUuid());
            amountTextView.setText(String.format("-%.2f", receipt.getTotalSum()));
        }
    }
}
