package io.mapwize.mapwizeuicomponents.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import io.mapwize.mapwizeuicomponents.Fragments.HomeFragment;
import io.mapwize.mapwizeuicomponents.Fragments.MallDetailsFragment;
import io.mapwize.mapwizeuicomponents.Fragments.ShopsList;
import io.mapwize.mapwizeuicomponents.MapView;
import io.mapwize.mapwizeuicomponents.Tools.SlideNavigation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import io.mapwize.mapwizeuicomponents.R;

public class Main extends AppCompatActivity {

    MaterialSearchView searchView;
    ListView lstView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
  Toolbar toolbar;
    private DatabaseReference reff;
    int flag;
    String mall ="East Coast Mall";
    BottomNavigationView navigation;
    Bundle bundle = new Bundle();

    String[] lstSource = {
            "Harry",
            "Ron",
            "Hermione",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);


        lstView = (ListView)findViewById(R.id.lstView);
        lstView.setVisibility(View.GONE);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstSource);
        lstView.setAdapter(adapter);
        searchView = (MaterialSearchView)findViewById(R.id.search_view);

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

/*        Intent intent = getIntent();
        final int mallname = intent.getIntExtra("mallname", 0);*/

          searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                lstView.setVisibility(View.VISIBLE);
                drawerLayout.addDrawerListener(actionBarDrawerToggle);
            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lstView will return default
                lstView.setVisibility(View.GONE);
          //      lstView = (ListView)findViewById(R.id.lstView);
                ArrayAdapter adapter = new ArrayAdapter(Main.this,android.R.layout.simple_list_item_1,lstSource);
                lstView.setAdapter(adapter);

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    List<String> lstFound = new ArrayList<String>();
                    for(String item:lstSource){
                        if(item.contains(newText))
                            lstFound.add(item);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(Main.this,android.R.layout.simple_list_item_1,lstFound);
                    lstView.setAdapter(adapter);
                }
                else{
                    //if search text is null
                    //return default
                    ArrayAdapter adapter = new ArrayAdapter(Main.this,android.R.layout.simple_list_item_1,lstSource);
                    lstView.setAdapter(adapter);
                }
                return true;
            }

        });
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
                case R.id.Adds:
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }


}
