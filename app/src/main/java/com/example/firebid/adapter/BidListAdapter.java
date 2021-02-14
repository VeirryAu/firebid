package com.example.firebid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebid.R;
import com.example.firebid.model.Bid;

import java.util.ArrayList;

public class BidListAdapter extends RecyclerView.Adapter<BidListAdapter.BidListViewHolder> {

    private Context context;
    private ArrayList<Bid> bidList;

    public BidListAdapter(Context context, ArrayList<Bid> bidList){
        this.context = context;
        this.bidList = bidList;
    }

    @NonNull
    @Override
    public BidListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bid_item, parent, false);
        return new BidListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidListViewHolder holder, int position) {
        Bid bid = bidList.get(position);
        holder.tvPrice.setText(String.valueOf(bid.getPrice()));
        holder.tvName.setText(bid.getUser());
    }

    @Override
    public int getItemCount() {
        return bidList.size();
    }

    public class BidListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        public BidListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBidName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
