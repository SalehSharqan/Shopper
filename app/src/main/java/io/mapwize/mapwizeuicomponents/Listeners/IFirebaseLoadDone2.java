package io.mapwize.mapwizeuicomponents.Listeners;

import io.mapwize.mapwizeuicomponents.Models.Products;

import java.util.List;

public interface IFirebaseLoadDone2 {

    void onFirebaseLoadSuccess(List<Products> ProductsList);
    void OnFirebaseLoadFail(String message);
}
