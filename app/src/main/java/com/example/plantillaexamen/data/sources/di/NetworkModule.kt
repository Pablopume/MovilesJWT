package com.example.plantillaexamen.data.sources.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import com.example.plantillaexamen.data.sources.remote.RemoteDataSource
import com.example.plantillaexamen.data.sources.remote.TokenAuthenticator
import com.example.plantillaexamen.data.sources.remote.ServiceInterceptor
import com.example.plantillaexamen.data.sources.service.AuthService
import com.example.plantillaexamen.data.sources.service.CustomerService
import com.example.plantillaexamen.data.sources.service.OrderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideHttpClient(interceptor: ServiceInterceptor, authenticator: TokenAuthenticator): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }



    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideConverterMoshiFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,

        ): Retrofit {
        return Retrofit.Builder()
            // 192.168.1.155
            .baseUrl("http://informatica.iesquevedo.es:2326/PabloSerrano/restaurant/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CustomerService =
        retrofit.create(CustomerService::class.java)

    @Singleton
    @Provides
    fun provideOrderService(retrofit: Retrofit): OrderService =
        retrofit.create(OrderService::class.java)


}