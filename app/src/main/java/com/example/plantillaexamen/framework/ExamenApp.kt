package com.example.plantillaexamen.framework

import android.app.Application
import androidx.room.Room
import com.example.plantillaexamen.data.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExamenApp : Application(){
override fun onCreate() {
        super.onCreate()
    }
    val appDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "customer-database"
    ).build()

}