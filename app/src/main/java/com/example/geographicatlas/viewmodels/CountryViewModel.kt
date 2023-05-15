package com.example.geographicatlas.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geographicatlas.models.Country
import com.example.geographicatlas.repository.CountryRepository
import com.example.geographicatlas.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CountryViewModel@Inject constructor(
    private val repository: CountryRepository
): ViewModel()  {

    private val _countryListState = MutableLiveData<Resource<List<Pair<String, ArrayList<Country>>>>?>(null)
    val countryListState: LiveData<Resource<List<Pair<String, ArrayList<Country>>>>?> = _countryListState

    private val _countryDetailsState = MutableLiveData<Resource<Country>?>(null)
    val countryDetailsState: LiveData<Resource<Country>?> = _countryDetailsState

    fun getRegionsCountryList() = viewModelScope.launch(Dispatchers.IO) {
        _countryListState.postValue(Resource.Loading)
        val result = repository.getRegionMap()
        _countryListState.postValue(result)
    }

    fun getCountryDetails(cca2: String) = viewModelScope.launch(Dispatchers.IO){
        _countryDetailsState.postValue(Resource.Loading)
        val result = repository.getCountryDetails(cca2)
        _countryDetailsState.postValue(result)
    }

    fun clearCountryDetailsState(){
        _countryDetailsState.value = null
    }

}