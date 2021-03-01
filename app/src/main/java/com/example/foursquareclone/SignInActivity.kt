package com.example.foursquareclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.parse.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ParseAnalytics.trackAppOpenedInBackground(intent)

        /*
        val parseObj = ParseObject("Fruits")
        parseObj.put("name", "orange")
        parseObj.put("calories", 200)
        parseObj.saveInBackground { e ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Saved".toString(), Toast.LENGTH_LONG).show()
            }
        }

        val query = ParseQuery.getQuery<ParseObject>("Fruits")
        query.whereLessThan("calories", 150)
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                println("Size: ${objects.size}")
                if (objects.size > 0) {
                    for (parseObj in objects) {
                        println("name: ${parseObj.get("name")}")
                        println("calories: ${parseObj.getInt("calories")}")
                    }
                }
            }
        }
        */
    }

    fun signIn(view: View) {
        val username = findViewById<EditText>(R.id.editTextUser).text.toString()
        ParseUser.logInInBackground(
            username,
            findViewById<EditText>(R.id.editTextPassword).text.toString(),
            LogInCallback { _, e ->
                if (e != null) {
                    Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Welcome $username", Toast.LENGTH_LONG).show()
                    val intent = Intent(applicationContext, LocationsActivity::class.java)
                    startActivity(intent)
                }
            }
        )
    }

    fun signUp(view: View) {
        var user = ParseUser()
        user.username = findViewById<EditText>(R.id.editTextUser).text.toString()
        user.setPassword(findViewById<EditText>(R.id.editTextPassword).text.toString())

        user.signUpInBackground { e: ParseException? ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "User Created", Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext, LocationsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}