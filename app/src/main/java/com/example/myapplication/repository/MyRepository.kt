package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.*
import com.example.myapplication.data.model.ArticlesModel
import com.example.myapplication.data.model.MyDAO
import com.example.myapplication.data.model.ResponseEntity
import com.example.myapplication.data.model.ResponseModel
import com.example.myapplication.data.remote.APIService
import com.example.myapplication.data.remote.RetrofitGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyRepository(val myDAO: MyDAO) {

    val CONTENT_TYPE = "application/json"
    val api = RetrofitGenerator.getInstance().create(APIService::class.java)

    val responseHandler = ResponseHandler()

    /**
     * This function will call our API & give us the response
     **/
    suspend fun getResponse() : Resource<ResponseModel> {

        val result = api.getEntity(CONTENT_TYPE)
        return responseHandler.handleSuccess(result)
    }

    /**
     * adding item to our database
     **/
    fun addItem(item : ResponseEntity){
        CoroutineScope(Dispatchers.IO).launch {
            myDAO.addEntity(item)
        }
    }

    /**
     * Give us the list of ResponseEntity from database
     **/
    fun getEntity(): LiveData<List<ResponseEntity>> {
        return myDAO.getEntity()
    }



}