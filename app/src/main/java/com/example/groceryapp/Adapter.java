package com.example.groceryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder>{

    private final Context context;
    public ArrayList<MainModel> productList;
    public ArrayList<MainModel> List;


    public Adapter(Context context, ArrayList<MainModel> productList) {
        this.context = context;
        this.productList = productList;
        this.List = productList;
    }
    public void setFilteredList(ArrayList<MainModel> filteredList, String filter){
        if(filter != null && filter.length() > 0){

            this.productList = filteredList;
            notifyDataSetChanged();
        }
        else{
            this.productList = List;
            notifyDataSetChanged();}


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
        holder.highlight.setText(model.getHighlight());
        holder.price.setText(model.getPrice());

        Glide.with(holder.url_Image.getContext())
                .load(model.getUrl_Image())
                .placeholder(com.google.firebase.database.collection.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal_background)
                .into(holder.url_Image);


        if(model.getStore().equals("WILLYS"))
            holder.store_image.setImageResource(R.drawable.willys);
        else if(model.getStore().equals("ICA"))
            holder.store_image.setImageResource(R.drawable.maxi);
        else if(model.getStore().equals("COOP"))
            holder.store_image.setImageResource(R.drawable.coop);
        else if(model.getStore().equals("LIDL"))
            holder.store_image.setImageResource(R.drawable.lidl);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder{
        ImageView url_Image, store_image;
        TextView title, price, highlight;

        public myViewHolder(@NonNull View itemView){
            super(itemView);
            store_image = itemView.findViewById(R.id.img2);
            url_Image = itemView.findViewById(R.id.img1);
            title = itemView.findViewById(R.id.nametext);
            highlight = itemView.findViewById(R.id.highlight);
            price = itemView.findViewById(R.id.price);
        }
    }
}
