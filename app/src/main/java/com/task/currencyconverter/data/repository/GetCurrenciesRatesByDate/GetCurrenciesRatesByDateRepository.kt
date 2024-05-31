package com.task.currencyconverter.data.repository.GetCurrenciesRatesByDate

import com.task.currencyconverter.data.model.CurrencyRatesResponseModel
import com.task.currencyconverter.utils.Result

interface GetCurrenciesRatesByDateRepository {
    suspend fun getCurrencyRatesByDate( date: String): Result<CurrencyRatesResponseModel>

}