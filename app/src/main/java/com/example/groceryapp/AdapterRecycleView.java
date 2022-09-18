package com.example.groceryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.HolderProduct>{

    private Context context;
    public ArrayList<ProductModel> productList;

    public AdapterRecycleView(Context context, ArrayList<ProductModel> productList){
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_row, parent, false);
        return new HolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
        ProductModel productModel = productList.get(position);
        String Name = productModel.getGroceryName();
        String Price = productModel.getPrice();
        String Weight = productModel.getDesc();
        String Image= productModel.getStoreName();

        holder.productName.setText(Name);
        holder.productPrice.setText(Price);
        holder.productWeight.setText(Weight);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class HolderProduct extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private TextView productName, productPrice, productWeight;
        public HolderProduct(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productWeight = itemView.findViewById(R.id.productWeight);


        }
    }
}
