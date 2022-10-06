package com.example.groceryapp;

import android.content.Context;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder>{

    private Context context;
    public ArrayList<MainModel> productList;

    public Adapter(Context context, ArrayList<MainModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        MainModel model = productList.get(position);
        holder.title.setText(model.getTitle());
        holder.price.setText(model.getPrice());
        holder.size.setText(model.getSize());

        Glide.with(holder.url_Image.getContext())
                .load(model.getUrl_Image())
                .placeholder(com.google.firebase.database.collection.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal_background)
                .into(holder.url_Image);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView url_Image;
        TextView title, price, size;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            url_Image = itemView.findViewById(R.id.img1);
            title = itemView.findViewById(R.id.nametext);
            price = itemView.findViewById(R.id.price);
            size = itemView.findViewById(R.id.weight);
        }
    }
}
