package com.example.foursquareclone

import android.app.Application
import com.parse.Parse

class StarterApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Parse.initialize(Parse.Configuration.Builder(this)
                .applicationId("myappID")
                .clientKey("07qf4hwsS3Ex")
                .server("http://18.130.102.63/parse")
                .build())
    }
}