package com.example.plantillaexamen.data.sources.di

import android.content.Context
import androidx.room.Room
import com.example.plantillaexamen.data.AppDatabase
import com.example.plantillaexamen.data.sources.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constantes.APP_DB
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesPersonaDao(articlesDatabase: AppDatabase) =
        articlesDatabase.customerDao()

    @Provides
    fun providesComidaDao(articlesDatabase: AppDatabase) =
        articlesDatabase.comidaDao()

    @Provides
    fun providesIngredienteDao(articlesDatabase: AppDatabase) =
        articlesDatabase.ingredienteDao()
}