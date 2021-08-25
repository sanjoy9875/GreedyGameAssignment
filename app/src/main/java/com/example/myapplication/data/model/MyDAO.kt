package com.example.myapplication.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDAO {

    /**
     * This function add list of item into our Database
     **/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEntity(responseEntity: ResponseEntity)

    /**
     * This function fetch the list of item from our Database
     **/
    @Query("select * from entity")
    fun getEntity(): LiveData<List<ResponseEntity>>

}