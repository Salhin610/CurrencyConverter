package com.task.currencyconverter.data.repository.GetCurrenciesRates
import com.task.currencyconverter.data.model.CurrencyRatesResponseModel
import com.task.currencyconverter.utils.Result

interface GetCurrencyRatesRepository {
    suspend fun getCurrencyRates(): Result<CurrencyRatesResponseModel>

}