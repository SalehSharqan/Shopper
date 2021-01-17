package io.mapwize.mapwizeuicomponents.Listeners;

import io.mapwize.mapwizeuicomponents.Models.Ads;

import java.util.List;

public interface IFirebaseLoadDone {

    void onFirebaseLoadSuccess(List<Ads> AdsList);
    void OnFirebaseLoadFail(String message);

}
