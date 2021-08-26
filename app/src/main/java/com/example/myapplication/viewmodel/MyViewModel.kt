package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.myapplication.data.model.ArticlesModel
import com.example.myapplication.data.model.ResponseEntity
import com.example.myapplication.data.model.ResponseModel
import com.example.myapplication.repository.MyRepository
import kotlinx.coroutines.Dispatchers

class MyViewModel(val repository: MyRepository) : ViewModel(){

    /**
     * Getting the response from api
     * */
    fun getResponse() : LiveData<ResponseModel>{
        return liveData(Dispatchers.IO){
            val data = repository.getResponse()

            emit(data.data!!)
        }
    }

    /**
     * adding item to our database
     */
    fun addItem(item : ResponseEntity){
        repository.addItem(item)
    }

    /**
     * getting the list of ResponseEntity from database
     **/
    fun getEntity(): LiveData<List<ResponseEntity>> {
        return repository.getEntity()
    }



}