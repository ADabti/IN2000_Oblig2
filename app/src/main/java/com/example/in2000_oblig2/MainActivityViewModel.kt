package com.example.in2000_oblig2

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")

class MainActivityViewModel : ViewModel() {
    private val data: DataSource = DataSource()

    //livedata for distrikt 1
    private val dis1Parti: MutableLiveData<MutableList<AlpacaParty>> by lazy {
        MutableLiveData<MutableList<AlpacaParty>>()
    }


    fun getdis1Parti(): MutableLiveData<MutableList<AlpacaParty>> {
        return dis1Parti
    }

    fun fetchdis1Parti() {
        CoroutineScope(Dispatchers.IO).launch {
            run {

                viewModelScope.launch(Dispatchers.IO) {
                    data.fetchDis1().also {

                        dis1Parti.postValue(it)
                    }
                }
            }
        }
    }

    //livedata for distrikt 2
    private val dis2Parti: MutableLiveData<MutableList<AlpacaParty>> by lazy {
        MutableLiveData<MutableList<AlpacaParty>>()
    }


    fun getdis2Parti(): MutableLiveData<MutableList<AlpacaParty>> {
        return dis2Parti
    }

    fun fetchdis2Parti() {
        CoroutineScope(Dispatchers.IO).launch {
            run {

                viewModelScope.launch(Dispatchers.IO) {
                    data.fetchDis2().also {

                        dis2Parti.postValue(it)
                    }
                }
            }
        }
    }


    //livedata for distrikt 3 med xml kall
    private val xmlParti: MutableLiveData<MutableList<AlpacaParty>> by lazy {
        MutableLiveData<MutableList<AlpacaParty>>()
    }


    fun getxmlParti(): MutableLiveData<MutableList<AlpacaParty>> {
        return xmlParti
    }

    fun fetchxmlParti() {
        CoroutineScope(Dispatchers.IO).launch {
            run {
                viewModelScope.launch(Dispatchers.IO) {
                    data.fetchXML().also {
                        xmlParti.postValue(it)
                    }
                }
            }
        }
    }


}

