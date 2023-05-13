package com.example.geographicatlas.models

class Country {
    var name: Name? = null
    var capital = ArrayList<String>()
    var flags: Flags? = null
    var continents = ArrayList<String>()
    var subregion: String = ""
    var population: Int = 0
    var currencies = HashMap<String,Currency>()
    var capitalInfo = HashMap<String, ArrayList<Float>>()
    var area: Float = 0F
    var timezones = ArrayList<String>()

    override fun toString(): String {
        return "{{{ name $name ," +
                "capital $capital ," +
                "flags ${flags?.png} ," +
                "continents $continents ," +
                "subregion $subregion ," +
                "population $population ," +
                "currencies $currencies ," +
                "capitalInfo $capitalInfo ," +
                "area $area ," +
                "timezomes $timezones }}}"
    }
}