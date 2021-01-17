package io.mapwize.mapwizeuicomponents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class ModifyProduct extends Fragment {
    EditText p_desc, p_id, P_name, p_price, p_shop, discount;
    DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    Products products;
    String s;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String Emaill;
    public String retailer_shop;
String key;
double afterDis, dis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.modify_product, container, false);

        p_price = rootview.findViewById(R.id.p_price);
        P_name = rootview.findViewById(R.id.prod_name);
        p_id = rootview.findViewById(R.id.p_id);
        p_desc = rootview.findViewById(R.id.p_desc);
        discount = rootview.findViewById(R.id.discount);

        reff = FirebaseDatabase.getInstance().getReference();
        products = new Products();
        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        Emaill = user.getEmail();
        Bundle b = this.getArguments();
        s = b.getString("productid");



        FirebaseDatabase.getInstance().getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                     products = dataSnapshot1.getValue(Products.class);
                    if (products.getId().equals(s)) {
                        p_id.setText(products.getId().trim());
                        P_name.setText(products.getP_name().trim());
                        p_price.setText(products.getP_price().trim());
                        p_desc.setText(products.getP_desc().trim());
                        discount.setText(products.getDiscount().trim());
                        afterDis = Double.parseDouble(products.getDisPrice().trim());
                        retailer_shop = products.getP_shop().trim();
                        key = products.getKey().trim();
                        Toast.makeText(getActivity(), key, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


        rootview.findViewById(R.id.Save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!p_desc.getText().toString().equals("") && !p_id.getText().toString().equals("") &&
                        !P_name.getText().toString().equals("") && !p_price.getText().toString().equals("")) {

                    products.setId(p_id.getText().toString().trim());
                    products.setP_name(P_name.getText().toString().trim());
                    products.setP_price(p_price.getText().toString().trim());
                    products.setP_desc(p_desc.getText().toString().trim());
                    products.setP_shop(retailer_shop);
                    products.setShopOwner(Emaill);

                    reff.child("Products").push().setValue(products);*/
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(key);

                products.setId(p_id.getText().toString().trim());
                products.setP_name(P_name.getText().toString().trim());
                products.setP_price(p_price.getText().toString().trim());
                products.setP_desc(p_desc.getText().toString().trim());
                products.setP_shop(retailer_shop); // its deleted automatically from firebase PROBLREM!!!!!!!!
                products.setShopOwner(Emaill);
                products.setKey(key);

                dis = Double.parseDouble(discount.getText().toString().trim());
                double OrigPrice = Double.parseDouble(products.getP_price().trim());
                double afterDis = OrigPrice - (OrigPrice * dis/100);
                products.setDiscount(Double.toString(dis));
                products.setDisPrice(Double.toString(afterDis));

                databaseReference.setValue(products);
                    Toast.makeText(getActivity(), "The product data modified successfully", Toast.LENGTH_LONG).show();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_ret_container, new ReProductsList(), "ReProductsList")
                            .addToBackStack("RetailerHome")
                            .commit();
               /* } else {
                    Toast.makeText(getContext(), "Please fill all boxes", Toast.LENGTH_LONG).show();
                }*/
            }
        });



        rootview.findViewById(R.id.modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").child(key);
                databaseReference.removeValue();
                try {
                    if (getFragmentManager() != null) {
                        RetailerHome fragB = new RetailerHome();
                        getFragmentManager().beginTransaction().replace(R.id.main_ret_container, fragB).commit();                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        rootview.findViewById(R.id.Addimg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bundle b = new Bundle();
                    b.putString("source", "Products");
                    AddImages fragB = new AddImages();
                    fragB.setArguments(b);
                    getFragmentManager().beginTransaction().replace(R.id.main_ret_container, fragB).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return rootview;
    }

}
