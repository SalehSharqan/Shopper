package io.mapwize.mapwizeuicomponents


import android.app.Application
import io.mapwize.mapwizesdk.core.MapwizeConfiguration

class MapwizeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val config = MapwizeConfiguration.Builder(this, "320b9df7d91056d9afd7d88e06665f2f").build()
        MapwizeConfiguration.start(config)
    }

}