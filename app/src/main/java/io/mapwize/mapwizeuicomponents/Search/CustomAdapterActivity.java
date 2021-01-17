package io.mapwize.mapwizeuicomponents.Search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.mapwize.mapwizeuicomponents.Activities.Main;
import io.mapwize.mapwizeuicomponents.Fragments.HomeFragment;
import io.mapwize.mapwizeuicomponents.Fragments.MallDetailsFragment;
import io.mapwize.mapwizeuicomponents.Fragments.ProfileFragment;
import io.mapwize.mapwizeuicomponents.Fragments.ShopsList;
import io.mapwize.mapwizeuicomponents.MapView;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.R;

public class CustomAdapterActivity extends AppCompatActivity implements View.OnClickListener, MaterialSearchBar.OnSearchActionListener {
    private MaterialSearchBar searchBar;
    private List<Products> suggestions = new ArrayList<>();
    private CustomSuggestionsAdapter customSuggestionsAdapter;
    DatabaseReference reff;
    Bundle bundle = new Bundle();
    String mall = "East Coast Mall";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_adapter);

        searchBar = findViewById(R.id.searchBar);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customSuggestionsAdapter = new CustomSuggestionsAdapter(inflater);
/*        Button addProductBtn = findViewById(R.id.button);
        addProductBtn.setOnClickListener(this);*/

        //enable searchbar callbacks
        searchBar.setOnSearchActionListener(this);
        searchBar.setMaxSuggestionCount(2);
        searchBar.setHint("Find Product..");


        reff = FirebaseDatabase.getInstance().getReference().child("Products");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Products products = dataSnapshot1.getValue(Products.class);
                    suggestions.add(products);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CustomAdapterActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


        customSuggestionsAdapter.setSuggestions(suggestions);
        searchBar.setCustomSuggestionAdapter(customSuggestionsAdapter);

        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
                // send the entered text to our filter and let it manage everything
                customSuggestionsAdapter.getFilter().filter(searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


    }

    //Bottom Navigation Bar START
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.Home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, new HomeFragment(), "HomeFragment")
                            .commit();
                    return true;

                case R.id.Mall:
                    bundle.putString("mallname", mall);
                    MallDetailsFragment fragB = new MallDetailsFragment();
                    fragB.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, fragB, "MallDetailsFragment")
                            .commit();
                    return true;
                case R.id.Shops:
                    bundle.putString("mallname", mall);
                    ShopsList fragC = new ShopsList();
                    fragC.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, fragC, "ShopsList")
                            .commit();
                    return true;
                case R.id.Map:
                    Intent x = new Intent(CustomAdapterActivity.this, MapView.class);
                    startActivity(x);
                    finish();
                    return true;

                case R.id.Profile:
                Bundle b = new Bundle();
                    b.putString("type", "Customer");
                    ProfileFragment fragd = new ProfileFragment();
                    fragd.setArguments(b);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, fragd, "ProfileFragment")
                            .commit();
            }
            return false;
        }
    };
    //END



    @Override
    public void onClick(View view) {
//        customSuggestionsAdapter.addSuggestion(new Products("Product", 100));
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
               // drawer.openDrawer(GravityCompat.START);
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.closeSearch();
                break;
        }

    }
}
