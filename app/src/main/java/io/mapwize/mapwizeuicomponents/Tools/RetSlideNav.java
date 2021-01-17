package io.mapwize.mapwizeuicomponents.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import io.mapwize.mapwizeuicomponents.Dialogs.About_Dialog;
import io.mapwize.mapwizeuicomponents.Fragments.AddImages;
import io.mapwize.mapwizeuicomponents.Fragments.CartFragment;
import io.mapwize.mapwizeuicomponents.Fragments.CategoriesFragment;
import io.mapwize.mapwizeuicomponents.Fragments.HistoryFragment;
import io.mapwize.mapwizeuicomponents.Fragments.ProfileFragment;
import io.mapwize.mapwizeuicomponents.Fragments.RetailerHome;
import io.mapwize.mapwizeuicomponents.Models.Users;
import io.mapwize.mapwizeuicomponents.R;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

public class RetSlideNav {
    int fragmnetholder;
    TextView user_name, home, category, about, logout, user_name_ret;
    CircleImageView user_img;
    FirebaseAuth firebaseAuth;
    FirebaseUser user ;

    DatabaseReference reff;


    public RetSlideNav(int fragmnetholder) {
        this.fragmnetholder = fragmnetholder;
    }


    public void initSlideMenu(final Activity activity, final FragmentManager fragmentManager, final DrawerLayout drawerLayout) {

        user_img = activity.findViewById(R.id.user_img);
        home = activity.findViewById(R.id.menu_home_ret);
      //  hot = activity.findViewById(R.id.menu_hot_ret);
       // cart = activity.findViewById(R.id.menu_cart_ret);
        //category = activity.findViewById(R.id.menu_cat_ret);
        about = activity.findViewById(R.id.menu_about_ret);
      //  history = activity.findViewById(R.id.menu_his_ret);
        logout = activity.findViewById(R.id.menu_logout_ret);
        user_name = activity.findViewById(R.id.user_name_ret);
        user_name_ret = activity.findViewById(R.id.user_name_ret);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        //user_name_ret.setText(user.getEmail());

        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Users users = dataSnapshot1.getValue(Users.class);
                    if (users.getUserType().equals("Retailer")) {
                        if (users.getR_Email().equals(user.getEmail())) {
                            Picasso.get()
                                    .load(users.getR_Photo())
                                    .into(user_img);
                            user_name.setText(users.getR_Name().toString().trim());

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
            /*    drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .add(R.id.main_fragment_container, new ProfileFragment(), "ProfileFragment")
                        .addToBackStack("ProfileFragment")
                        .commit();

                Bundle b = new Bundle();
                b.putString("type", "Retailer");
                ProfileFragment fragB = new ProfileFragment();
                fragB.setArguments(b);
                fragmentManager
                        .beginTransaction()
                        .add(R.id.main_fragment_container,fragB, "ProfileFragment")
                        .addToBackStack("ProfileFragment")
                        .commit();*/
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new RetailerHome(), "RetailerHome")
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


