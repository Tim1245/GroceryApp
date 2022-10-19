package com.example.groceryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavoritesActivity extends AppCompatActivity {
    ArrayList<String> favs = new ArrayList<String>();
    static final String STORA_COOP_VALSVIKEN = "STORA_COOP_VALSVIKEN";
    static final String ICA_MAXI = "ICA-MAXI";
    static final String LIDL = "LIDL";
    static final String WILLYS = "WILLYS";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton filterByCategory = findViewById(R.id.filterByCategory);

        if (UserManagement.isUserLoggedIn()) {
            AccountSettings.AddUserSettingsUpdateCallback(this, (UserSettingsMessage settings) -> {
                Log.i("ACCOUNT SETTINGS LOG", "Updating switches, " + settings.toString());
                if (settings.IsWILLYSFavoured())
                    favs.add(WILLYS);
                if (settings.IsCOOPFavoured())
                    favs.add(STORA_COOP_VALSVIKEN);
                if (settings.IsLIDLFavoured())
                    favs.add(LIDL);
                if (settings.IsICAFavoured())
                    favs.add(ICA_MAXI);

            });

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
            recyclerView = findViewById(R.id.rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            productList = new ArrayList<>();

            adapter = new Adapter(FavoritesActivity.this, productList);
            recyclerView.setAdapter(adapter);
            ref = FirebaseDatabase.getInstance().getReference("Butik");

            loadFavorites(ref, "All");
        } else {
            Toast.makeText(getApplicationContext(), "Logga in för att använda favroit-funktionen", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);

        }



        //Categories
        filterByCategory.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FavoritesActivity.this);
            builder.setTitle("Choose Category: ").setItems(Categories, (dialogInterface, i) -> {
                        String selected = Categories[i];
                        categories.setText(getString(R.string.showing) + " "+ selected);
                        loadFavorites(ref,selected);


                    })
                    .show();
        });

    }

    public void loadFavorites(DatabaseReference reference, String filter){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot ds1: snapshot.getChildren()) {
                    String storeName = ds1.getKey();
                    for(String fav: favs){
                        if(storeName.equals(fav)){
                            for (DataSnapshot ds : ds1.getChildren()) {
                                String category = ds.child("category").getValue(String.class);
                                if(filter.equals("All")){
                                    MainModel model = ds.getValue(MainModel.class);
                                    productList.add(model);
                                }
                                else if (category.equals(filter)) {
                                    MainModel model = ds.getValue(MainModel.class);
                                    productList.add(model);
                                }
                                Collections.sort(productList, new FavoritesActivity.CustomComparator());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }


                }


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






