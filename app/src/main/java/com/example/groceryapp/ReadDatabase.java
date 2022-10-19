package com.example.groceryapp;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class ReadDatabase extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference ref;
    private ArrayList<MainModel> productList;
    private Adapter adapter;
    private TextView categories;
    public static final String[] Categories={
            "All",
            "Vegetariskt",
            "Vegan",
            "Frukt & grönsaker",
            "Kött, fågel, fisk",
            "Mejeri, ost och ägg",
            "Dryck",
            "Glass, godis & snacks",
            "Bröd & kakor",
            "Övrigt"
    };

    // Get the database instance and store into object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Search bar
        SearchView searchView = findViewById(R.id.searchView);
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
        ImageButton filterByCategory = findViewById(R.id.filterByCategory);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String butik = getIntent().getStringExtra("Butik");

        productList = new ArrayList<>();

        adapter = new Adapter(ReadDatabase.this, productList);
        recyclerView.setAdapter(adapter);
        ref = FirebaseDatabase.getInstance().getReference("Butik");

        if (butik.equals("ALL")) {
            loadAll(ref, "All");
        } else {
            loadOne(ref, butik, "All");
        }


        //Categories
        filterByCategory.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ReadDatabase.this);
            builder.setTitle("Choose Category: ").setItems(Categories, (dialogInterface, i) -> {
                        String selected = Categories[i];
                        categories.setText(getString(R.string.showing) + " " + selected);

                        if (!butik.equals("ALL")) {
                            loadOne(ref, butik, selected);
                        } else if(butik.equals("ALL")) {
                            loadAll(ref, selected);
                        }


                    })
                    .show();
        });

    }

    public void loadOne(DatabaseReference reference, String butik, String filter){
        reference.child(butik).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String category = ds.child("category").getValue(String.class);
                    if (filter.equals("All")) {
                        MainModel model = ds.getValue(MainModel.class);
                        productList.add(model);
                    } else if (category.equals(filter)) {
                        MainModel model = ds.getValue(MainModel.class);
                        productList.add(model);
                    }
                    Collections.sort(productList, new CustomComparator());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadAll(DatabaseReference reference, String filter){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds1: snapshot.getChildren()) {
                    for (DataSnapshot ds : ds1.getChildren()) {
                        String category = ds.child("category").getValue(String.class);
                        if (filter.equals("All")) {
                            MainModel model = ds.getValue(MainModel.class);
                            productList.add(model);
                        } else if (category.equals(filter)) {
                            MainModel model = ds.getValue(MainModel.class);
                            productList.add(model);
                        }
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

    /*public void loadFiltered(DatabaseReference reference,String butik, String filter){
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

    public void loadAllFiltered(DatabaseReference reference, String filter){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds1: snapshot.getChildren()) {
                    for (DataSnapshot ds : ds1.getChildren()) {
                        String category = ds.child("category").getValue(String.class);
                        if (category.equals(filter)) {
                            MainModel model = ds.getValue(MainModel.class);
                            productList.add(model);
                        }
                        Collections.sort(productList, new CustomComparator());
                        adapter.notifyDataSetChanged();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

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
        if(filteredList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Hittade inga varor", Toast.LENGTH_SHORT).show();
        }
        adapter.setFilteredList(filteredList, filter);


    }
    public class CustomComparator implements Comparator<MainModel> {
        @Override
        public int compare(MainModel o1, MainModel o2) {
            o1.setPrice(o1.getPrice().replaceAll("[^.0123456789]",""));
            o2.setPrice(o2.getPrice().replaceAll("[^.0123456789]",""));
            if(!o1.getPrice().contains("."))
                o1.setPrice(o1.getPrice() + ".00");
            if(!o2.getPrice().contains("."))
                o2.setPrice(o2.getPrice() + ".00");
            return Double.compare(Double.parseDouble(o1.getPrice()),Double.parseDouble(o2.getPrice()));
        }
    }
}
