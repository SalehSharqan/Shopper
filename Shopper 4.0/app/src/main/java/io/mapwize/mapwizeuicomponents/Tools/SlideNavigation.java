package io.mapwize.mapwizeuicomponents.Tools;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alexzh.circleimageview.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.mapwize.mapwizeuicomponents.Activities.Login;
import io.mapwize.mapwizeuicomponents.Dialogs.About_Dialog;
import io.mapwize.mapwizeuicomponents.Fragments.CartFragment;
import io.mapwize.mapwizeuicomponents.Fragments.CategoriesFragment;
import io.mapwize.mapwizeuicomponents.Fragments.HistoryFragment;
import io.mapwize.mapwizeuicomponents.Fragments.HomeFragment;
import io.mapwize.mapwizeuicomponents.Fragments.HotFragment;
import io.mapwize.mapwizeuicomponents.Fragments.ProfileFragment;
import io.mapwize.mapwizeuicomponents.R;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

public class SlideNavigation {
    int fragmnetholder;
    TextView user_name, home, hot, cart, category, about, logout, history;
    CircleImageView user_img;

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
        about = activity.findViewById(R.id.menu_about);
        history = activity.findViewById(R.id.menu_his);
        logout = activity.findViewById(R.id.menu_logout);
        user_name = activity.findViewById(R.id.user_name);
       firebaseAuth = FirebaseAuth.getInstance();
       user = firebaseAuth.getCurrentUser();
       user_name.setText(user.getEmail());



        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .add(R.id.main_fragment_container, new ProfileFragment(), "ProfileFragment")
                        .addToBackStack("ProfileFragment")
                        .commit();
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
                        .replace(fragmnetholder, new CategoriesFragment(), "CategoriesFragment")
                        .addToBackStack("CategoriesFragment")
                        .commit();

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new CartFragment(), "CartFragment")
                        .addToBackStack("CartFragment")
                        .commit();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager
                        .beginTransaction()
                        .replace(fragmnetholder, new HistoryFragment(), "HistoryFragment")
                        .addToBackStack("HistoryFragment")
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
