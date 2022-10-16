package com.project.digitalpaymentsbook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder> {

    Context context;
    ArrayList<PurchaseDetails> myList;

    public MyAdaptor(Context context, ArrayList<PurchaseDetails> list) {
        this.context = context;
        this.myList = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.purchase_history,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdaptor.MyViewHolder holder, int position) {

        PurchaseDetails purchaseDetails = myList.get(position);

        Log.d("Display product name:",purchaseDetails.getProductName());
        holder.Date.setText(purchaseDetails.getPurchaseDate());
        holder.productName.setText(purchaseDetails.getProductName());
        holder.productPrice.setText(purchaseDetails.getProductPrice());

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Date,productName,productPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.setPurchaseDate);
            productName = itemView.findViewById(R.id.setProductName);
            productPrice = itemView.findViewById(R.id.setProductPrice);
        }
    }
}
