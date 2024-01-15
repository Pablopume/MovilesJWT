package com.example.plantillaexamen.data.sources.di
import com.example.plantillaexamen.data.TokenAuthenticator
import com.example.plantillaexamen.data.sources.remote.ServiceInterceptor
import com.example.plantillaexamen.data.sources.service.AuthService
import com.example.plantillaexamen.data.sources.service.CustomerService
import com.example.plantillaexamen.data.sources.service.OrderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
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
    fun provideInterceptor(): Interceptor {
        return ServiceInterceptor()
    }

    @Provides
    @Singleton
    fun provideAuthenticator(authService: AuthService): Authenticator {
        return TokenAuthenticator(authService)
    }


    @Singleton
    @Provides
    fun provideHttpClient(interceptor: Interceptor, autenthicator: Authenticator): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .authenticator(autenthicator)
            .connectTimeout(15, TimeUnit.SECONDS)

            .build()
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
            .baseUrl("http://informatica.iesquevedo.es:2326/PabloSerrano/restaurant/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CustomerService =
        retrofit.create(CustomerService::class.java)

    @Singleton
    @Provides
    fun provideOrderService(retrofit: Retrofit): OrderService =
        retrofit.create(OrderService::class.java)

}