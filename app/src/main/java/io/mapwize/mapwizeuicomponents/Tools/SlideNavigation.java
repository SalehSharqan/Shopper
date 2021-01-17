package io.mapwize.mapwizeuicomponents.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import io.mapwize.mapwizeuicomponents.Activities.Login;
import io.mapwize.mapwizeuicomponents.Activities.Main;
import io.mapwize.mapwizeuicomponents.Dialogs.About_Dialog;
import io.mapwize.mapwizeuicomponents.Fragments.CartFragment;
import io.mapwize.mapwizeuicomponents.Fragments.CategoriesFragment;
import io.mapwize.mapwizeuicomponents.Fragments.Favorites;
import io.mapwize.mapwizeuicomponents.Fragments.HistoryFragment;
import io.mapwize.mapwizeuicomponents.Fragments.HomeFragment;
import io.mapwize.mapwizeuicomponents.Fragments.HotFragment;
import io.mapwize.mapwizeuicomponents.Fragments.MallsFragment;
import io.mapwize.mapwizeuicomponents.Fragments.Notes;
import io.mapwize.mapwizeuicomponents.Fragments.ProfileFragment;
import io.mapwize.mapwizeuicomponents.Fragments.Receipts;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import io.mapwize.mapwizeuicomponents.Search.CustomSuggestionsAdapter;

public class SlideNavigation {
    int fragmnetholder;
    TextView user_name, home, hot, cart, category, about, logout, history, notes;
    CircleImageView user_img;

    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    FirebaseUser user ;

    public SlideNavigation(int fragmnetholder) {
        this.fragmnetholder = fragmnetholder;
    }


    public void initSlideMenu(final Activity activity, final FragmentManager fragmentManager,
                              final DrawerLayout drawerLayout) {
        user_img = activity.findViewById(R.id.user_img);
        home = activity.findViewById(R.id.menu_home);
        hot = activity.findViewById(R.id.menu_hot);
        cart = activity.findViewById(R.id.menu_cart);
        category = activity.findViewById(R.id.menu_cat);
        notes = activity.findViewById(R.id.notes);

        about = activity.findViewById(R.id.menu_about);
        history = activity.findViewById(R.id.menu_his);
        logout = activity.findViewById(R.id.menu_logout);
        user_name = activity.findViewById(R.id.user_name);
       firebaseAuth = FirebaseAuth.getInstance();
       user = firebaseAuth.getCurrentUser();
     //  user_name.setText(user.getEmail());

        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if (users.getUserType().equals("Customer")) {
                        if (users.getC_Email().equals(user.getEmail())) {
                            Picasso.get()
                                    .load(users.getC_Photo())
                                    .into(user_img);
                            user_name.setText(users.getC_Name().toString().trim());
                            category.setText(users.getC_mall().trim());

                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new HomeFragment(), "HomeFragment")
                        .commit();
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new Notes(), "Notes")
                        .commit();
            }
        });

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new HotFragment(), "HotFragment")
                        .addToBackStack("HotFragment")
                        .commit();

            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new MallsFragment(), "MallsFragment")
                        .addToBackStack("MallsFragment")
                        .commit();

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new Favorites(), "Favorites")
                        .addToBackStack("Favorites")
                        .commit();

            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new Receipts(), "Receipts")
                        .addToBackStack("Receipts")
                        .commit();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                About_Dialog about_dialog = new About_Dialog();
                about_dialog.show(fragmentManager, about_dialog.getTag());
            }
        });

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            /*    fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new LoginFragment(), "LoginFragment")
                        .addToBackStack("LoginFragment")
                        .commit();*/
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent x = new Intent(activity, Login.class);
                x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(x);

            }
        });

    }

}
