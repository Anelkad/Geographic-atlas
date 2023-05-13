package com.example.geographicatlas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.beust.klaxon.Klaxon
import com.example.geographicatlas.databinding.ActivityMainBinding
import com.example.geographicatlas.models.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class MainActivity : AppCompatActivity() {

    fun getRequest(sUrl: String): String? {

        var client = OkHttpClient();

        var result: String? = null
        try {
            val url = URL(sUrl)
            val request = Request.Builder().url(url).build()

            val response = client.newCall(request).execute()
            result = response.body?.string()
            //Log.d("qwerty: ", result!!)
        } catch (err: Error) {
            print("Error when executing get request: " + err.localizedMessage)
        }
        return result
    }

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(R.id.fragmentContainer)


        var countries: List<Country>? = null
        lifecycleScope.launch(Dispatchers.IO) {
            val result = getRequest("https://restcountries.com/v3.1/all")
            if (result != null) {
                try {
                    // Parse result string JSON to data class
                    //todo заменить klaxon на другое
                    countries = Klaxon().parseArray<Country>(result)
                    // Update view model
                    Log.d("countries: ", countries?.size.toString())
                    sortCountries(countries!!)
                } catch (err: Error) {
                    print("Error when parsing JSON: " + err.localizedMessage)
                }
            } else {
                print("Error: Get request returned no response")
            }
        }

    }
    fun sortCountries(countries: List<Country>){
            val regionMap = HashMap<String, ArrayList<Country>>()
            for (i in countries){
                if (regionMap.contains(i.continents[0]))
                    regionMap[i.continents[0]]?.add(i)
                else
                { regionMap[i.continents[0]] = ArrayList()
                    regionMap[i.continents[0]]?.add(i)}
            }
            for (i in regionMap.keys){
                Log.d("region", i)
                for (j in regionMap[i]!!){
                    Log.d("country",j.name?.common!!)
                }
            }

    }
}