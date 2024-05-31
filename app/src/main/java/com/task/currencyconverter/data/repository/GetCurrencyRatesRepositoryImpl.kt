package com.task.currencyconverter.data.repository

import com.task.currencyconverter.data.api.ApiService
import com.task.currencyconverter.data.model.CurrencyRatesResponseModel
import com.task.currencyconverter.utils.Result
import org.json.JSONException
import javax.inject.Inject
import org.json.JSONObject

class GetCurrencyRatesRepositoryImpl @Inject constructor(private val apiService: ApiService) : GetCurrencyRatesRepository {
    override suspend fun getCurrencyRates(): Result<CurrencyRatesResponseModel> {
        return try {
            val response = apiService.
            getCurrenciesRates()
            if (response.isSuccessful) {
                val response = response.body()
                if (response != null) {
                    Result.Success(response)
                } else {
                    Result.Error(Exception("Invalid response from server"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    JSONObject(errorBody).getString("message")
                } catch (e: JSONException) {
                    "Unknown error occurred"
                }
                Result.Error(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}