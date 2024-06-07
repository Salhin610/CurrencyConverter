package com.task.currencyconverter.ui.home

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.currencyconverter.R
import com.task.currencyconverter.domain.usecase.GetCurrencyRatesUseCase
import com.task.currencyconverter.utils.Result
import com.task.currencyconverter.utils.Utils.isInternetAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase,
    application: Application
) : AndroidViewModel(application) {
    val _rates = HashMap<String, Double>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val currency1Index = MutableLiveData<Int>()
    val currency2Index = MutableLiveData<Int?>()
    private val appContext = getApplication<Application>().applicationContext
    val amount1 = MutableLiveData<String>()
    val amount2 = MutableLiveData<String?>()

    private var isConversionInProgress = false

    init {
        currency1Index.value = 0
        currency2Index.value = 0
        amount1.value = "1.0"
        amount2.value = "1.0"
        // Fetch exchange rates from the server
        fetchRates()
        amount1.observeForever { if (!isConversionInProgress) convertCurrency(true) }
        amount2.observeForever { if (!isConversionInProgress) convertCurrency(false) }
        currency1Index.observeForever { convertCurrency(true) }
        currency2Index.observeForever { convertCurrency(false) }
    }

    fun fetchRates() {
        viewModelScope.launch {
            try {
                if (isInternetAvailable(appContext))
                when(val response = getCurrencyRatesUseCase.getCurrenciesRate()){
                    is Result.Success ->{
                       _rates.putAll(response.data.rates)
                    }
                    is Result.Error ->{
                        _error.postValue(response.exception?.message)
                    }
                }
                else
                    _error.postValue("Check Internet Connection")
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun convertCurrency(fromAmount1: Boolean) {
        val index1 = currency1Index.value ?: 0
        val index2 = currency2Index.value ?: 0
        val amount1Value = amount1.value?.toDoubleOrNull() ?: 1.0
        val amount2Value = amount2.value?.toDoubleOrNull() ?: 1.0

        val rate1 = _rates[appContext.resources.getStringArray(R.array.currency_array)[index1]] ?: 1.0
        val rate2 = _rates[appContext.resources.getStringArray(R.array.currency_array)[index2]] ?: 1.0

        isConversionInProgress = true
        if (fromAmount1) {
            amount2.value = String.format("%.3f", amount1Value * rate2 / rate1)
        } else {
            amount1.value = String.format("%.3f", amount2Value * rate1 / rate2)
        }
        isConversionInProgress = false
    }
    @SuppressLint("SuspiciousIndentation")
    fun getTodayRateForUnit(): String{
        val index1 = currency1Index.value ?: 0
        val index2 = currency2Index.value ?: 0

        val rate1 = _rates[appContext.resources.getStringArray(R.array.currency_array)[index1]] ?: 1.0
        val rate2 = _rates[appContext.resources.getStringArray(R.array.currency_array)[index2]] ?: 1.0


            return String.format("%.3f",  rate2 / rate1)

    }
    fun swapCurrencies() {
        val tempIndex = currency1Index.value
        currency1Index.value = currency2Index.value
        currency2Index.value = tempIndex!!

        // Swap the amounts as well to reflect the change
        val tempAmount = amount1.value
        amount1.value = amount2.value
        amount2.value = tempAmount!!

        // Trigger conversion to update amounts after swap
        convertCurrency(true)
    }

    fun getCommonCurrenciesRates(): DoubleArray {
        val commonCurrencyArray = appContext.resources.getStringArray(R.array.common_currency_array)
        val index1 = currency1Index.value ?: 0

        val baseCurrency = appContext.resources.getStringArray(R.array.currency_array)[index1]
        val baseRate = _rates[baseCurrency]
        if (baseRate == null) {
            _error.postValue("The rate for the base currency $baseCurrency is not available")
            return DoubleArray(0)        }
        // List to store the rates against the base currency
        val ratesList = mutableListOf<Double>()

        // Loop through the commonCurrencyArray and calculate the rates against the base currency
        for (currency in commonCurrencyArray) {
            val rate = _rates[currency]
            if (rate != null) {
                val rateAgainstBase = rate / baseRate
                ratesList.add(rateAgainstBase)
            } else {
                ratesList.add(Double.NaN) // NaN to indicate rate not available
            }
        }
        return ratesList.toDoubleArray()
    }
}