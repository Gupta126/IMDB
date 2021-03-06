package com.rahulgupta.imdbmovieapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulgupta.imdbmovieapp.repository.db.DatabaseService
import com.rahulgupta.imdbmovieapp.model.Result
import com.rahulgupta.imdbmovieapp.network.ApiService
import com.rahulgupta.imdbmovieapp.utils.Helper
import com.rahulgupta.imdbmovieapp.utils.Keys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiService: ApiService,
    private val helper: Helper,
    private val databaseService: DatabaseService
) : ViewModel() {

    val nowPlayingMutableLiveData = MutableLiveData<List<Result>>()
    val msg = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    init {
        getData()
        loading.postValue(false)
    }

    fun getData() {
        viewModelScope.launch {
            try {
                coroutineScope {
                    delay(1000)
                    loading.postValue(true)
                    try {
                        val resultDao = databaseService.resultDao()
                        val popular = async {
                            val popularResults = apiService.getPopularMovies()
                            val nowPlayingResults = apiService.getNowPlayingMovies()
//                            for(i:Result in popularResults?.results!!){
//                                i.type=Keys.POPULAR
//                                i.pKey=Keys.POPULAR+""+i.id
//                            }
                            for (i: Result in nowPlayingResults?.results!!) {
                                i.type = Keys.NOW_PLAYING
                                i.pKey = Keys.NOW_PLAYING + "" + i.id
                            }
                            resultDao.clear()
                            //resultDao.insertMany(popularResults.results!!)
                            resultDao.insertMany(nowPlayingResults.results!!)
                        }
                        popular.await().let {
                            nowPlayingMutableLiveData.postValue(
                                withContext(Dispatchers.Default) {
                                    resultDao.getResultsByType(
                                        Keys.NOW_PLAYING
                                    )
                                }
                            )
                        }
                    } catch (exception: Exception) {
                        Log.i("popular exeception", exception.message.toString())
                    }

                    loading.postValue(false)
                }
            } catch (unknownHostException: UnknownHostException) {
                Log.i("error", unknownHostException.message.toString())
                msg.postValue("No internet connection")
                getDataFromDatabase()
            } catch (exception: Exception) {
                Log.i("error", exception.message.toString())
                getDataFromDatabase()
            }

        }

    }

    private suspend fun getDataFromDatabase() {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                coroutineScope {
                    delay(1000)
                    try {
                        val nowPlaying =
                            async { databaseService.resultDao().getResultsByType(Keys.NOW_PLAYING) }
                        nowPlayingMutableLiveData.postValue(nowPlaying.await())
                    } catch (exception: Exception) {
                        Log.i("nowplaying execeptiondb", exception.message.toString())
                    }
                }
            } catch (e: Exception) {
                Log.i("error from db", e.message.toString())
                nowPlayingMutableLiveData.postValue(ArrayList())
            } finally {
                loading.postValue(false)
            }
        }
    }
}