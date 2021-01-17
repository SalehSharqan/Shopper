package io.mapwize.mapwizeuicomponents.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import androidx.core.view.GravityCompat;
import io.mapwize.mapwizeuicomponents.Fragments.CartFragment;
import io.mapwize.mapwizeuicomponents.Fragments.HomeFragment;
import io.mapwize.mapwizeuicomponents.Fragments.HotFragment;
import io.mapwize.mapwizeuicomponents.Fragments.MallDetailsFragment;
import io.mapwize.mapwizeuicomponents.Fragments.MallsFragment;
import io.mapwize.mapwizeuicomponents.Fragments.ProfileFragment;
import io.mapwize.mapwizeuicomponents.Fragments.ShopsList;
import io.mapwize.mapwizeuicomponents.MapView;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.Search.CustomAdapterActivity;
import io.mapwize.mapwizeuicomponents.Search.CustomSuggestionsAdapter;
import io.mapwize.mapwizeuicomponents.Tools.SlideNavigation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import io.mapwize.mapwizeuicomponents.R;

public class Main extends AppCompatActivity  {

    ListView lstView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private DatabaseReference reff;
    int flag;
     String mall = "East Coast Mall";
     String sal;
    BottomNavigationView navigation;
    Bundle bundle = new Bundle();
    CircleImageView changemall;
    TextView mallname;
/*
    private MaterialSearchBar searchBar2;
    private List<Products> suggestions = new ArrayList<>();
    private CustomSuggestionsAdapter customSuggestionsAdapter;
    DatabaseReference reff2;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigation.setVisibility(View.VISIBLE);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);



        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, new HomeFragment(), "HomeFragment")
                .commit();

        SlideNavigation slideNavigation = new SlideNavigation(R.id.main_fragment_container);
        slideNavigation.initSlideMenu(Main.this, getSupportFragmentManager(), drawerLayout);


        /*toolbar.findViewById(R.id.imagelogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x1 = new Intent(Main.this, CustomAdapterActivity.class);
                startActivity(x1);
                finish();
            }
        });*/



/*
        searchBar2 = findViewById(R.id.searchBar2);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        customSuggestionsAdapter = new CustomSuggestionsAdapter(inflater);
        searchBar2.setOnSearchActionListener(this);
        searchBar2.setMaxSuggestionCount(2);
        searchBar2.setHint("Find Product..");
        reff2 = FirebaseDatabase.getInstance().getReference().child("Products");
        reff2.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(Main.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        customSuggestionsAdapter.setSuggestions(suggestions);
        searchBar2.setCustomSuggestionAdapter(customSuggestionsAdapter);

        searchBar2.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar2.getText());
                // send the entered text to our filter and let it manage everything
                customSuggestionsAdapter.getFilter().filter(searchBar2.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });*/
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
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
                    Intent x = new Intent(Main.this, MapView.class);
                    startActivity(x);
                    finish();
                    return true;

                case R.id.Profile:
                  Bundle b = new Bundle();
                    b.putString("type", "Customer");
                    CartFragment fragd = new CartFragment();
                    fragd.setArguments(b);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, fragd, "ProfileFragment")
                            .commit();

                    return true;
            }
            return false;
        }
    };
    //END


    public void setmallname(String mall)
    {
        this.mall  = mall;
    }

    public void hidenav()
    {
        navigation.setVisibility(View.GONE);
    }
    public void shownav()
    {
        navigation.setVisibility(View.VISIBLE);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/


/*

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

                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar2.closeSearch();
                break;
        }

    }
*/


}
