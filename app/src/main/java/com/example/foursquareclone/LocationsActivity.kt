package com.example.foursquareclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery

class LocationsActivity : AppCompatActivity() {

    var namesArray = ArrayList<String>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_place, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.add_place) {
            val intent = Intent(applicationContext, PlaceNameActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)
        getParseData()

        findViewById<ListView>(R.id.listView).setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, DetailActivity::class.java)
            intent.putExtra("name", namesArray[i])
            startActivity(intent)
        }
    }

    private fun getParseData() {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, namesArray)
        findViewById<ListView>(R.id.listView).adapter = arrayAdapter

        val query = ParseQuery.getQuery<ParseObject>("Locations")
        query.findInBackground { objects, e ->
            if (e != null) {
                Toast.makeText(applicationContext, e.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            } else {
                if (!objects.isEmpty()) {
                    namesArray.clear()

                    for (parseObject in objects) {
                        val name = parseObject.get("name") as String
                        namesArray.add(name)
                    }

                    arrayAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}