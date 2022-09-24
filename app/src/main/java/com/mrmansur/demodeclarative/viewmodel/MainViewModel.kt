package com.mrmansur.demodeclarative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrmansur.android_imperative.model.TVShowDetails
import com.mrmansur.demodeclarative.model.TVShow
import com.mrmansur.demodeclarative.model.TVShowPopular
import com.mrmansur.demodeclarative.repository.TVShowRepository
import com.mrmansur.demodeclarative.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val tvShowRepository: TVShowRepository) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()
    val tvShowPopular = MutableLiveData<TVShowPopular>()


    init {
        apiTVShowPopular(1)
    }


    val tvShowsFromDB = MutableLiveData<List<TVShow>>()


    val tvShowDetails = MutableLiveData<TVShowDetails>()

    fun apiTVShowPopular(page: Int) {
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTVShowPopular(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    Logger.d("MainViewModel", resp.toString())
                    tvShowPopular.postValue(resp)
                    tvShowsFromApi.postValue(resp!!.tv_shows)
                    isLoading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    fun insertTVShowToDB(tvShow: TVShow){
        viewModelScope.launch {
            tvShowRepository.insertTVShowToDB(tvShow)
        }
    }

//    fun getTVShowsFromDB(){
//        viewModelScope.launch {
//            val tvShows = tvShowRepository.getTVShowsFromDB()
//            tvShowsFromDB.postValue(tvShows)
//        }
//    }
//
//
//
//    fun deleteTvShowsFromDB(){
//        viewModelScope.launch {
//            tvShowRepository.deleteTvShowsFromDB()
//        }
//    }
}