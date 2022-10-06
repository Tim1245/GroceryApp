package com.example.groceryapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReadDatabase extends AppCompatActivity {
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    DatabaseReference ref;
    private ArrayList<MainModel> productList;
    private Adapter adapter;
    // Get the database instance and store into object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String butik = getIntent().getStringExtra("Butik");

        /*FirebaseRecyclerOptions<MainModel> options=
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Butik").child(butik), MainModel.class)
                        .build();*/
        productList = new ArrayList<>();

        adapter = new Adapter(ReadDatabase.this, productList);
        recyclerView.setAdapter(adapter);
        ref = FirebaseDatabase.getInstance().getReference("Butik");
        if(butik.equals("ALL")){
            loadAll(ref);
        } else {
            loadOne(ref, butik);
        }



        //Categories
        /*FirebaseRecyclerOptions<MainModel> options=
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Butik").child(butik).orderByChild("category").startAt("Frukt & Grönt").endAt("Frukt & Grönt"+"\uf8ff"), MainModel.class)
                        .build();*/

        //mainAdapter = new MainAdapter(options);
        //recyclerView.setAdapter(mainAdapter);


    }

    /*@Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }*/
    public void loadOne(DatabaseReference reference, String butik){
        reference.child(butik).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String category = ds.child("category").getValue(String.class);
                        String price = ds.child("price").getValue(String.class);

                            MainModel model = ds.getValue(MainModel.class);
                            productList.add(model);}

                adapter.notifyDataSetChanged();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Collections.sort(productList, Comparator.comparing(MainModel::getTitle));
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadAll(DatabaseReference reference){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds1: snapshot.getChildren()) {

                    for (DataSnapshot ds : ds1.getChildren()) {
                        String category = ds.child("category").getValue(String.class);
                        String price = ds.child("price").getValue(String.class);


                            MainModel model = ds.getValue(MainModel.class);
                            productList.add(model);}

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadFiltered(DatabaseReference reference, String butik, String filter){
        reference.child(butik).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String category = ds.child("category").getValue(String.class);
                    String price = ds.child("price").getValue(String.class);
                    if (category.equals(filter)) {
                        MainModel model = ds.getValue(MainModel.class);
                        productList.add(model);
                    }
                }
                adapter.notifyDataSetChanged();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
