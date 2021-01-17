package io.mapwize.mapwizeuicomponents;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.mapwize.mapwizeuicomponents.Adapters.Products_Adapter;
import io.mapwize.mapwizeuicomponents.Models.Products;
import io.mapwize.mapwizeuicomponents.databinding.ProductsBinding;

public class ProductView extends AppCompatActivity  {
    private ProductsViewModel productsViewModel;
    private ProductsBinding binding;
    private ArrayList<Products> productsList;
    private Products_Adapter adapter;
    private String shopname;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        shopname = intent.getStringExtra("shopname");

        recyclerView = findViewById(R.id.recyclerView);
        ProductsViewModel viewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);

        LiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        binding = DataBindingUtil.setContentView(ProductView.this, R.layout.products);

        binding.setLifecycleOwner(this);

        binding.setProductsViewModel(productsViewModel);

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    // update the UI here with values in the snapshot
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                    {
                        Products products = dataSnapshot1.getValue(Products.class);
                        if(products.getP_shop().equals(shopname)){
                            productsList.add(products);
                            Toast.makeText(ProductView.this, shopname, Toast.LENGTH_SHORT).show();

                        }
                    }
                    adapter = new Products_Adapter(getApplicationContext(), productsList,"Customer");
                    binding.recyclerView.setAdapter(adapter);

                }
            }
        });
    }
}




