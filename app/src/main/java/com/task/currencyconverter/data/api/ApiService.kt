package com.task.currencyconverter.data.api

import com.task.currencyconverter.data.model.CurrencyRatesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface ApiService {

    companion object {
        const val BASE_URL = "https://currency-conversion-and-exchange-rates.p.rapidapi.com/"
    }
    @GET("latest")
    suspend fun getCurrenciesRates(): Response<CurrencyRatesResponseModel>
    @GET("{date}")
    suspend fun getCurrenciesRatesByDate(@Path("date") date: String): Response<CurrencyRatesResponseModel>


}
