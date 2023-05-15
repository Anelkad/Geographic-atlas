package com.example.geographicatlas.models

import java.lang.Math.abs

class Country {
    var name: Name? = null
    var capital = ArrayList<String>()
    var flags: Flags? = null
    var maps: Maps? = null
    var cca2: String = ""
    var continents = ArrayList<String>()
    var subregion: String = ""
    var population: Int = 0
    var currencies = HashMap<String,Currency>()
    var capitalInfo: CapitalInfo? = null
    var area: Float = 0F
    var timezones = ArrayList<String>()
    var isExpanded = false

    override fun toString(): String {
        return "{{{ name ${name?.common} ," +
                "capital ${capital} ," +
                "flags ${flags?.png} ," +
                "maps ${maps?.googleMaps} ," +
                "continents $continents ," +
                "subregion $subregion ," +
                "population $population ," +
                "currencies ${currencies.keys} ," +
                "capitalInfo ${capitalInfo?.latlng} ," +
                "area $area ," +
                "timezones $timezones }}}"
    }
    fun changeExpanded(){
        this.isExpanded = !this.isExpanded
    }

    private fun toStringWithZero(i: Int): String{
        return when (i) {
            in 0..9 -> "00".plus(i)
            in 10..99 -> "0".plus(i)
            else -> i.toString()
        }
    }

    private fun getPopulationText(): String{
        val mln = 1000000
        return if (population>=mln) "${((population+mln-1)/mln)} mln"
        else if(population>=1000) "${population/1000} ${toStringWithZero(population%1000)}"
        else (population%1000).toString()
    }

    private fun getAreaText(): String{
        val mln = 1000000
        val area = area.toInt()
        return if (area>=mln) "${area/mln} ${toStringWithZero(area%mln/1000)} ${toStringWithZero(area%1000)}"
        else if (area>=1000) "${area/1000} ${toStringWithZero(area%1000)}"
        else "${area%1000}"
    }
    fun toDetailsMap(): Map<String,String>{
        var currencyList = ArrayList<String>()
        for (i in currencies.toList()) currencyList.add(
            "${i.second.name} (${i.second.symbol}) (${i.first})"
        )

        val coordinates = capitalInfo?.latlng
        var coordinatesText = ArrayList<String>()
        if (coordinates != null) {
            for (i in coordinates)
                coordinatesText.add(
                    ("${i.toInt()}°")
                        .plus(if (abs(i)%1*60<10) "0${(abs(i)%1*60).toInt()}'" else "${(abs(i)%1*60).toInt()}'")
                )
        }

        return mapOf(
            "Capital:" to if (capital.isEmpty()) "" else capital[0],
            "Capital coordinates:" to coordinatesText.joinToString(separator = ", "),
            "Population:" to getPopulationText(),
            "Area:" to "${getAreaText()} km\u00B2",
            "Currency:" to currencyList.joinToString(separator = "\n"),
            "Region:" to subregion,
            "Timezones:" to timezones.joinToString(separator = "\n")
        )
    }

    fun toExpandedDetailsMap(): Map<String,String>{
        var currencyList = ArrayList<String>()

        for (i in currencies.toList()) currencyList.add(
            if (currencies.keys.size>1) i.second.name
            else "${i.second.name} (${i.second.symbol}) (${i.first})"
        )

        return mapOf(
            "Population:" to getPopulationText(),
            "Area:" to "${getAreaText()} km\u00B2",
            "Currencies:" to currencyList.joinToString(separator = ", ")
        )
    }

}