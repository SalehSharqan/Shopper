package io.mapwize.mapwizeuicomponents.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.mapwize.mapwizeuicomponents.Fragments.OffersList;
import io.mapwize.mapwizeuicomponents.Fragments.ReProductsList;
import io.mapwize.mapwizeuicomponents.Fragments.RetailerHome;
import io.mapwize.mapwizeuicomponents.Fragments.retCart;
import io.mapwize.mapwizeuicomponents.Fragments.retProfile;
import io.mapwize.mapwizeuicomponents.R;
import io.mapwize.mapwizeuicomponents.Tools.RetSlideNav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class RetailerMain extends AppCompatActivity {
    public static Boolean sessionR = false;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Button ChangeMall;
    FirebaseAuth firebaseAuth;
    FirebaseUser user ;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailermain);
        BottomNavigationView navigation = findViewById(R.id.navigation_ret);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.ret_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_ret);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

      getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_ret_container, new RetailerHome(), "RetailerHome")
                .commit();
        RetSlideNav RetSlideNav = new RetSlideNav(R.id.main_ret_container);
        RetSlideNav.initSlideMenu(RetailerMain.this, getSupportFragmentManager(), drawerLayout);

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
                case R.id.homeR:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_ret_container, new RetailerHome(), "RetailerHome")
                            .commit();
                    return true;case R.id.products:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_ret_container, new ReProductsList(), "ReProductsList")
                            .commit();
                    return true;
                case R.id.offers:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_ret_container, new OffersList(), "OffersList")
                            .commit();
                    return true;
                case R.id.Profile:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_ret_container, new retCart(), "retCart")
                            .commit();
                    return true;
            }
            return false;
        }
    };
    //END


}
