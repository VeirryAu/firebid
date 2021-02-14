package com.example.firebid.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebid.BidActivity;
import com.example.firebid.R;
import com.example.firebid.model.Product;

import java.util.List;
import java.text.DecimalFormat;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private List<Product> products;
    private Context context;

    public ProductListAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product room = products.get(position);
        holder.name.setText(room.getProductName());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.winner.setText(room.getWinner().equalsIgnoreCase("") ? "" : "Winner: " + room.getWinner());
        if (room.getHighestBid().equalsIgnoreCase("")) {
            holder.highestBid.setVisibility(View.GONE);
        } else {
            Integer number = Integer.parseInt(room.getHighestBid());
            String rtn = formatter.format(number).replace(",", ".");
            holder.highestBid.setText(room.getHighestBid().equalsIgnoreCase("") ? "" : "Highest Bid: Rp " + rtn);
            holder.highestBid.setVisibility(View.VISIBLE);
        }
        if (room.getWinner().equalsIgnoreCase("")) {
            holder.winner.setVisibility(View.GONE);
        } else {
            holder.winner.setVisibility(View.VISIBLE);
        }
        holder.description.setText(room.getDescription().equalsIgnoreCase("") ?
                "Mulai bid anda.." : room.getDescription());
        holder.productRow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("ROOM_ID", room.getProductName());
                Intent intent = new Intent(context, BidActivity.class);
                intent.putExtra("PRODUCT_ID", room.getProductId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(products != null){
            return products.size();
        }else{
            return 0;
        }
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView name, winner, highestBid, description;
        LinearLayout productRow;

        public ProductViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.productName);
            highestBid = (TextView) itemView.findViewById(R.id.highestBid);
            winner = (TextView) itemView.findViewById(R.id.winner);
            description = (TextView) itemView.findViewById(R.id.roomDescription);
            productRow = (LinearLayout) itemView.findViewById(R.id.roomRow);
        }
    }
}
