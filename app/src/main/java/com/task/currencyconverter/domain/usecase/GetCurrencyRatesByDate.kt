package com.task.currencyconverter.domain.usecase

import com.task.currencyconverter.data.model.CurrencyRatesResponseModel
import com.task.currencyconverter.data.repository.GetCurrenciesRatesByDate.GetCurrenciesRatesByDateRepository
import com.task.currencyconverter.utils.Result
import javax.inject.Inject

class GetCurrencyRatesByDate @Inject constructor(private val getCurrencyRatesByDateRepository: GetCurrenciesRatesByDateRepository) {
    suspend fun getCurrenciesRate(date: String): Result<CurrencyRatesResponseModel> {
        return getCurrencyRatesByDateRepository.getCurrencyRatesByDate(date)
    }
}