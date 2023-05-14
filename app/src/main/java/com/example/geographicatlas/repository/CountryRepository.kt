package com.example.geographicatlas.repository

import com.example.geographicatlas.models.Country
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
            val url = URL(sUrl)
            val request = Request
                .Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            result = response.body?.string()
        } catch (err: Error) {
            print("Error when executing get request: " + err.localizedMessage)
        }
        return result
    }
    override fun getRegionMap(): Resource<HashMap<String, ArrayList<Country>>>{
        var resource: Resource<HashMap<String, ArrayList<Country>>> = Resource.Loading
        val result = getRequest(COUNTRY_LIST)
        if (result != null) {
            resource = try {
                val gson = Gson()
                val countries: List<Country> = gson.fromJson(result, object : TypeToken<List<Country?>?>() {}.type)
                //Log.d("countries: ", countries?.size.toString())
                Resource.Success(sortCountriesByRegion(countries))
            } catch (e: Exception) {
                e.printStackTrace()
                Resource.Failure(e)
            }
        }
        return resource
    }
     override fun sortCountriesByRegion(countries: List<Country>): HashMap<String, ArrayList<Country>>{
        val regionMap = HashMap<String, ArrayList<Country>>()
        for (i in countries){
            if (regionMap.contains(i.continents[0]))
                regionMap[i.continents[0]]?.add(i)
            else
            { regionMap[i.continents[0]] = ArrayList()
                regionMap[i.continents[0]]?.add(i)}
        }
//        for (i in regionMap.keys){
//            Log.d("region", i)
//            Log.d("region", regionMap[i]?.size.toString())
//            for (j in regionMap[i]!!){
//                Log.d("country",j.toString())
//            }
//        }
        return regionMap
    }

}

interface CountryRepository{
    fun getRequest(sUrl: String): String?
    fun getRegionMap(): Resource<HashMap<String, ArrayList<Country>>>
    fun sortCountriesByRegion(countries: List<Country>): HashMap<String, ArrayList<Country>>
}