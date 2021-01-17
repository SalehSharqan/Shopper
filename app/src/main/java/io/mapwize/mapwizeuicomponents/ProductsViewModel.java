package io.mapwize.mapwizeuicomponents;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProductsViewModel extends ViewModel {

    private static final DatabaseReference PRODUCTS_REF =
            FirebaseDatabase.getInstance().getReference("Products");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PRODUCTS_REF);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }


}
