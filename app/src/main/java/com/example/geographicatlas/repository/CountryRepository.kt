package com.example.geographicatlas.repository

import com.example.geographicatlas.models.Country
import com.example.geographicatlas.utils.COUNTRY_DETAILS
import com.example.geographicatlas.utils.COUNTRY_LIST
import com.example.geographicatlas.utils.Resource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.net.URL
import javax.inject.Inject

class CountryRepositoryImp @Inject constructor(): CountryRepository {
     override fun getRequest(sUrl: String): String? {
        val client = OkHttpClient()
        var result: String? = null
        try {
            val request = Request
                .Builder()
                .url(URL(sUrl))
                .build()
            val response = client.newCall(request).execute()
            result = response.body?.string()
        } catch (err: Error) {
            print("Error when executing get request: " + err.localizedMessage)
        }
        return result
    }
    override fun getRegionMap(): Resource<List<Pair<String, ArrayList<Country>>>>{
        var resource: Resource<List<Pair<String, ArrayList<Country>>>> = Resource.Loading
        val result = getRequest(COUNTRY_LIST)
        if (result != null) {
            resource = try {
                val countries: List<Country> = Gson()
                    .fromJson(result, object : TypeToken<List<Country?>?>() {}.type)
                //Log.d("countries: ", countries?.size.toString())
                Resource.Success(groupCountriesGroupByRegion(countries))
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure(e)
            }
        }
        return resource
    }

    override fun getCountryDetails(cca2:String): Resource<Country> {
        var resource: Resource<Country> = Resource.Loading
        val result = getRequest(COUNTRY_DETAILS+cca2)
        if (result != null) {
            resource = try {
                val country: List<Country> = Gson().fromJson(result, object : TypeToken<List<Country?>?>() {}.type)
                Resource.Success(country[0])
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure(e)
            }
        }
        return resource
    }

    override fun groupCountriesGroupByRegion(countries: List<Country>): List<Pair<String, ArrayList<Country>>> {
        val regionMap = HashMap<String, ArrayList<Country>>()
        for (i in countries) {
            if (regionMap.contains(i.continents[0]))
                regionMap[i.continents[0]]?.add(i)
            else {
                regionMap[i.continents[0]] = ArrayList()
                regionMap[i.continents[0]]?.add(i)
            }
        }
//        for (i in regionMap.keys){
//            Log.d("region", i)
//            Log.d("region", regionMap[i]?.size.toString())
//            for (j in regionMap[i]!!){
//                Log.d("country",j.toString())
//            }
//        }
        return regionMap.toList()
    }

}

interface CountryRepository{
    fun getRequest(sUrl: String): String?
    fun getRegionMap(): Resource<List<Pair<String, ArrayList<Country>>>>
    fun getCountryDetails(cca2: String): Resource<Country>
    fun groupCountriesGroupByRegion(countries: List<Country>): List<Pair<String, ArrayList<Country>>>
}