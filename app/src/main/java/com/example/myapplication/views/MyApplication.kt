package com.example.myapplication.views

import android.app.Application
import com.example.myapplication.data.MyDatabase
import com.example.myapplication.repository.MyRepository

class MyApplication : Application() {

    private val entityDAO by lazy {
        val roomDatabase = MyDatabase.getRoomDatabase(this)
        roomDatabase.getEntityDao()
    }

    val repository by lazy {
        MyRepository(entityDAO)
    }

}