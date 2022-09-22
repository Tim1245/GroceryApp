package com.example.groceryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.productName.setText(model.getProductName());
        holder.productPrice.setText(model.getProductPrice());
        holder.productWeight.setText(model.getProductWeight());

        Glide.with(holder.productImage.getContext())
                .load(model.getProductImage())
                .placeholder(com.google.firebase.database.collection.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal_background)
                .into(holder.productImage);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView productImage;
        TextView productName, productPrice, productWeight;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            productImage = itemView.findViewById(R.id.img1);
            productName = itemView.findViewById(R.id.nametext);
            productPrice = itemView.findViewById(R.id.price);
            productWeight = itemView.findViewById(R.id.weight);
        }
    }
}
