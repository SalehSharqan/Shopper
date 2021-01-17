package io.mapwize.mapwizeuicomponents.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
import io.mapwize.mapwizeuicomponents.Models.Shops;
import io.mapwize.mapwizeuicomponents.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class Add_Product extends Fragment {
    EditText p_desc, p_id, P_name, p_price, discount;
    CheckBox disCheck;
    DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    Products products;
    String s;
    FirebaseAuth firebaseAuth1 = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String Emaill;
    public String retailer_shop;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.add_product, container, false);

        p_price = rootview.findViewById(R.id.p_price);
        P_name = rootview.findViewById(R.id.prod_name);
        p_id = rootview.findViewById(R.id.p_id);
        p_desc = rootview.findViewById(R.id.p_desc);
        disCheck = rootview.findViewById(R.id.disCheck);
        discount = rootview.findViewById(R.id.discount);
        reff = FirebaseDatabase.getInstance().getReference();
        products = new Products();
        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        Emaill = user.getEmail();

        FirebaseDatabase.getInstance().getReference().child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Shops shops = dataSnapshot1.getValue(Shops.class);
                    if (shops.getOwnerEmail().equals(Emaill)) {
                        retailer_shop = shops.getShopName();
                        Toast.makeText(getActivity(), retailer_shop, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });





        rootview.findViewById(R.id.Save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!p_desc.getText().toString().equals("") && !p_id.getText().toString().equals("") &&
                        !P_name.getText().toString().equals("") && !p_price.getText().toString().equals("")) {
                    products.setId(p_id.getText().toString().trim());
                    products.setP_name(P_name.getText().toString().trim());
                    products.setP_price(p_price.getText().toString().trim());
                    products.setP_desc(p_desc.getText().toString().trim());
                    products.setP_shop(retailer_shop);
                    products.setShopOwner(Emaill);
                    String key = reff.child("Products").push().getKey();
                    products.setKey(key);
                    if(disCheck.isChecked())
                    {
                        //discount.setVisibility(View.VISIBLE); //should
                        double dis = Double.parseDouble(discount.getText().toString().trim());
                        products.setDiscount(Double.toString(dis));
                        double OrigPrice = Double.parseDouble(p_price.getText().toString().trim());
                        double afterDis = OrigPrice - (OrigPrice * dis/100);
                        products.setDisPrice(Double.toString(afterDis));
                    }
                    else
                    {
                        products.setDisPrice(Double.toString(0));
                        products.setDiscount(Double.toString(0));


                    }


                    reff.child("Products").child(key).setValue(products);
                    Toast.makeText(getActivity(), "The product added successfully", Toast.LENGTH_LONG).show();

                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_ret_container, new RetailerHome(), "RetailerHome")
                            .addToBackStack("RetailerHome")
                            .commit();
                }
                else {
                    Toast.makeText(getContext(), "Please fill all boxes", Toast.LENGTH_LONG).show();
                }
            }
        });



        rootview.findViewById(R.id.Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (getFragmentManager() != null) {
                        RetailerHome fragB = new RetailerHome();
                        getFragmentManager().beginTransaction().replace(R.id.main_ret_container, fragB).commit();                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return rootview;
    }

}
