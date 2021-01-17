package io.mapwize.mapwizeuicomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.indoorlocation.core.IndoorLocation
import io.indoorlocation.manual.ManualIndoorLocationProvider
import io.mapwize.mapwizeui.MapwizeFragment
import io.mapwize.mapwizeui.MapwizeFragmentUISettings
import io.mapwize.mapwizesdk.api.Floor
import io.mapwize.mapwizesdk.api.MapwizeObject
import io.mapwize.mapwizesdk.api.Place
import io.mapwize.mapwizesdk.map.MapOptions
import io.mapwize.mapwizesdk.map.MapwizeMap
import kotlinx.android.synthetic.main.map_view.*


class MapView : AppCompatActivity(), MapwizeFragment.OnFragmentInteractionListener {

    private var mapwizeFragment: MapwizeFragment? = null
    private var mapwizeMap: MapwizeMap? = null
    private var locationProvider: ManualIndoorLocationProvider? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_view)

        // Uncomment and fill place holder to test MapwizeUI on your venue
        val opts = MapOptions.Builder()
                .restrictContentToOrganization("5ea00ef6d2db0a00168be11c")
                .restrictContentToVenueId("5ea31570c18a0600162252cd")
                .centerOnVenue("5ea31570c18a0600162252cd")
                .centerOnPlace("5ea5fff9a1fb5200169a580e")
                .build()
        // Uncomment and change value to test different settings configuration
        val uiSettings = MapwizeFragmentUISettings.Builder()
                .menuButtonHidden(true)
                .followUserButtonHidden(false)
                .floorControllerHidden(false)
                .compassHidden(true)
                .build()
        mapwizeFragment = MapwizeFragment.newInstance(opts, uiSettings)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(fragmentContainer.id, mapwizeFragment!!)
        ft.commit()

    }

    /**
     * Fragment listener
     */
    override fun onFragmentReady(mapwizeMap: MapwizeMap) {
        this.mapwizeMap = mapwizeMap
        this.locationProvider = ManualIndoorLocationProvider()
        mapwizeMap.setIndoorLocationProvider(this.locationProvider!!)

        mapwizeMap.addOnClickListener {
            val il = IndoorLocation("manual", it.latLngFloor.latitude,
                    it.latLngFloor.longitude,
                    it.latLngFloor.floor,
                    System.currentTimeMillis())
            this.locationProvider?.setIndoorLocation(il)
        }
    }

    override fun onMenuButtonClick() {

    }

    override fun onInformationButtonClick(mapwizeObject: MapwizeObject?) {

    }

    override fun onFollowUserButtonClickWithoutLocation() {
        Log.i("Debug", "onFollowUserButtonClickWithoutLocation")
    }

    override fun shouldDisplayInformationButton(mapwizeObject: MapwizeObject?): Boolean {
        Log.i("Debug", "shouldDisplayInformationButton")
        when (mapwizeObject) {
            is Place -> return true
        }
        return false
    }

    override fun shouldDisplayFloorController(floors: MutableList<Floor>?): Boolean {
        Log.i("Debug", "shouldDisplayFloorController")
        if (floors == null || floors.size <= 1) {
            return false
        }
        return true
    }

}
