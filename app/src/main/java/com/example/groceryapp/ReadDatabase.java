package com.example.groceryapp;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ReadDatabase extends AppCompatActivity {
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    DatabaseReference ref;
    private ArrayList<MainModel> productList;
    private Adapter adapter;
    private SearchView searchView;
    private ImageButton filterByCategory;
    private TextView categories;

    public static final String[] Categories={
            "ALL",
            "Category 2",
            "Category 3",
            "Category 4",
            "Category 5",
            "Category 6"
    };
    // Get the database instance and store into object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Search bar
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return false;
            }
        });
        categories = findViewById(R.id.categoriesTextview);
        filterByCategory = findViewById(R.id.filterByCategory);
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
        filterByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReadDatabase.this);
                builder.setTitle("Choose Category: ").setItems(Categories, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String selected = Categories[i];
                                categories.setText("Showing: "+ selected);

                                if(selected.equals("ALL"))
                                    loadOne(ref, butik);


                                else
                                    loadFiltered(ref, butik, selected);

                            }
                        })
                        .show();
            }
        });


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
                           MainModel model = ds.getValue(MainModel.class);
                            productList.add(model);}
                    Collections.sort(productList, new CustomComparator());
                    adapter.notifyDataSetChanged();
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
                            MainModel model = ds.getValue(MainModel.class);
                            productList.add(model);}

                }
                Collections.sort(productList, new CustomComparator());
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
                    if (category.equals(filter)) {
                        MainModel model = ds.getValue(MainModel.class);
                        productList.add(model);
                    }
                }
                Collections.sort(productList, new CustomComparator());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void searchList(String filter){
        ArrayList<MainModel> filteredList = new ArrayList<>();

        for(MainModel item: productList){
            if(filter.equals("ALL")){
                filteredList.add(item);

            }
            else if(item.getTitle().toLowerCase().contains(filter.toLowerCase())){
                filteredList.add(item);

            }
        }
        if(!filteredList.isEmpty()) {
            adapter.setFilteredList(filteredList, filter);
        }

    }
    public class CustomComparator implements Comparator<MainModel> {
        @Override
        public int compare(MainModel o1, MainModel o2) {
            o1.setPrice(o1.getPrice().replaceAll("[^\\.0123456789]",""));
            o2.setPrice(o2.getPrice().replaceAll("[^\\.0123456789]",""));
            if(!o1.getPrice().contains("."))
                o1.setPrice(o1.getPrice() + ".00");
            if(!o2.getPrice().contains("."))
                o2.setPrice(o2.getPrice() + ".00");
            return Double.compare(Double.valueOf(o1.getPrice()),Double.valueOf(o2.getPrice()));
        }
    }
}
