package com.task.currencyconverter.di

import com.task.currencyconverter.data.api.ApiService
import com.task.currencyconverter.data.api.ApiService.Companion.BASE_URL
import com.task.currencyconverter.data.repository.GetCurrenciesRates.GetCurrencyRatesRepository
import com.task.currencyconverter.data.repository.GetCurrenciesRates.GetCurrencyRatesRepositoryImpl
import com.task.currencyconverter.data.repository.GetCurrenciesRatesByDate.GetCurrenciesRatesByDateRepository
import com.task.currencyconverter.data.repository.GetCurrenciesRatesByDate.GetCurrenciesRatesByDateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .header("X-RapidAPI-Key", "2c7eb373e8msh66c59d171615aabp12d511jsn3d856436f53c")
                    .header("X-RapidAPI-Host", "currency-conversion-and-exchange-rates.p.rapidapi.com")


                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build()) // Set the OkHttpClient with logging interceptor
            .build()
            .create(ApiService::class.java)
    }
    @Provides
    @Singleton
    fun provideGetCurrencyRatesRepository(apiService: ApiService): GetCurrencyRatesRepository {
        return GetCurrencyRatesRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideGetCurrencyRatesByDateRepository(apiService: ApiService): GetCurrenciesRatesByDateRepository {
        return GetCurrenciesRatesByDateRepositoryImpl(apiService)
    }
}