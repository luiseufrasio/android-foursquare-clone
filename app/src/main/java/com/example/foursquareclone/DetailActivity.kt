package com.example.foursquareclone

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {
    var chosenPlace = ""
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val intent = intent
        chosenPlace = intent.getStringExtra("name").toString()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val query = ParseQuery<ParseObject>("Locations")
        query.whereEqualTo("name", chosenPlace)
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (!objects.isEmpty()) {
                    for (parseObject in objects) {
                        val image = parseObject.get("image") as ParseFile
                        image.getDataInBackground { data, e ->
                            if (e != null) {
                                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                            } else {
                                val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                                findViewById<ImageView>(R.id.detailImageView).setImageBitmap(bitmap)
                                val name = parseObject.get("name") as String
                                val latitude = parseObject.get("latitude") as String
                                val longitude = parseObject.get("longitude") as String
                                val type = parseObject.get("type") as String
                                val atmosphere = parseObject.get("atmosphere") as String

                                findViewById<TextView>(R.id.nameTextView).text = name
                                findViewById<TextView>(R.id.typeTextView).text = type
                                findViewById<TextView>(R.id.atmosphereTextView).text = atmosphere

                                val latitudeDouble = latitude.toDouble()
                                val longitudeDouble = longitude.toDouble()

                                val chosenLocation = LatLng(latitudeDouble, longitudeDouble)
                                mMap.addMarker(MarkerOptions().position(chosenLocation).title(name))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenLocation, 17f))
                            }
                        }
                    }
                }
            }
        }
    }
}