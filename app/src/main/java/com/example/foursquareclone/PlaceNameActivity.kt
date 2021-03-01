package com.example.foursquareclone

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import java.lang.Exception

var globalName = ""
var globalType = ""
var globalAtmosphere = ""
var globalImage : Bitmap? = null

class PlaceNameActivity : AppCompatActivity() {

    var chosenImage : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_name)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun selectImg(view: View) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 2) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            val selected = data.data
            try {
                chosenImage = MediaStore.Images.Media.getBitmap(this.contentResolver, selected)
                findViewById<ImageView>(R.id.imageView2).setImageBitmap(chosenImage)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun next(view: View) {
        globalImage = chosenImage
        globalName =  findViewById<EditText>(R.id.editTextName).text.toString()
        globalType =  findViewById<EditText>(R.id.editTextType).text.toString()
        globalAtmosphere =  findViewById<EditText>(R.id.editTextAtmosphere).text.toString()

        val intent = Intent(applicationContext, MapsActivity::class.java)
        startActivity(intent)
    }
}