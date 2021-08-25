package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.MyDAO
import com.example.myapplication.data.model.ResponseEntity

@Database(entities = [ResponseEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getEntityDao(): MyDAO

    companion object{

        private var INSTANCE : MyDatabase? = null

        /**
         * Creating a Database called entity_database
         * */
        fun getRoomDatabase(context: Context): MyDatabase{

            if (INSTANCE == null){

                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "entity_database")

                builder.fallbackToDestructiveMigration()
                INSTANCE =  builder.build()
                return INSTANCE!!
            }else{
                return INSTANCE!!
            }

        }

    }

}