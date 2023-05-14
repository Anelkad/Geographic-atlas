package com.example.geographicatlas.models

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
    var capitalInfo = HashMap<String, ArrayList<Float>>()
    var area: Float = 0F
    var timezones = ArrayList<String>()
    var isExpanded = false

    override fun toString(): String {
        return "{{{ name ${name?.common} ," +
                "capital ${capital} ," +
                "flags ${flags?.png} ," +
                "maps ${maps?.googleMaps} ," +
                "continents ${continents} ," +
                "subregion $subregion ," +
                "population $population ," +
                "currencies ${currencies.keys} ," +
                "capitalInfo ${capitalInfo.values} ," +
                "area $area ," +
                "timezomes $timezones }}}"
    }
    fun changeExpanded(){
        this.isExpanded = !this.isExpanded
    }
}