package com.task.currencyconverter.domain.usecase

import com.task.currencyconverter.data.model.CurrencyRatesResponseModel
import com.task.currencyconverter.data.repository.GetCurrenciesRates.GetCurrencyRatesRepository
import javax.inject.Inject
import com.task.currencyconverter.utils.Result

class GetCurrencyRatesUseCase @Inject constructor(private val getCurrencyRatesRepository: GetCurrencyRatesRepository){
    suspend fun getCurrenciesRate(): Result<CurrencyRatesResponseModel> {
        return getCurrencyRatesRepository.getCurrencyRates()
    }
}