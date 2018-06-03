package app.android.easygroup.easyparking.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.domain.parkinglot.Price;

public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.ViewHolder> {

    private List<Price> prices;

    public PricesAdapter(List<Price> prices) {
        this.prices = prices == null ? new ArrayList<Price>() : prices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Price price = prices.get(position);

        holder.typeTextView.setText(price.type);
        holder.amountTextView.setText(price.amount + " / " + price.unit);
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView typeTextView, amountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            typeTextView = itemView.findViewById(R.id.type_text_view);
            amountTextView = itemView.findViewById(R.id.amount_text_view);
        }
    }
}
