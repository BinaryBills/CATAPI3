package com.cis435.catapi3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cis435.catapi3.databinding.ActivityMainBinding
import com.cis435.catapi3.ui.main.MainFragment
import com.cis435.catapi3.ui.main.Spinner
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(),  Spinner.OnItemSelectedListener  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var spinnerFrag: Spinner

    override fun onItemSelected(selectedItem: String) {

        // You can handle the selected item here, or forward it to the DescFragment
        println("Clicked")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       var catClass = Cat()
        catClass = loadCatData(catClass)

        val spinnerButtonFragment = supportFragmentManager.findFragmentById(R.id.spinner) as Spinner?

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    fun loadCatData(catClass: Cat): Cat {
        val catUrl =
            "https://api.thecatapi.com/v1/breeds" + "?api_key=live_hK3Ng8WIujkrAl2eSYkafua3j7wYPNG3D2n5Ey6oxM9XvM6AIInNMfSa9Gf3dIPw"
        val queue = Volley.newRequestQueue(this)
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, catUrl,
            { response ->
                var catsArray: JSONArray = JSONArray(response)

                for (i in 0 until catsArray.length()) {

                    var theCat: JSONObject = catsArray.getJSONObject(i)
                    val catName = "${theCat.getString("name")}"
                    val description = "${theCat.getString("description")}"
                    val origin = "${theCat.getString("origin")}"
                    val temperament = "${theCat.getString("temperament")}"

                    catClass.breedList.add(catName)
                    catClass.descList.add(description)
                    catClass.originList.add(origin)
                    catClass.tempList.add(temperament)

                    println(catName)
                    println(description)
                    println(origin)
                    println(temperament)

                }
            },
            Response.ErrorListener {
                Log.i("MainActivity", "That didn't work!")
            })
// Add the request to the RequestQueue.
        queue.add(stringRequest)
        return catClass
    }//end printCatData


}



