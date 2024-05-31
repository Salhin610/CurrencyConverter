package com.task.currencyconverter.ui.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.task.currencyconverter.domain.usecase.GetCurrencyRatesByDateUseCase
import com.task.currencyconverter.utils.Result
import com.task.currencyconverter.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getCurrencyRatesUseCase: GetCurrencyRatesByDateUseCase,
    application: Application
) : AndroidViewModel(application) {
    private val appContext = getApplication<Application>().applicationContext
    val currency1 = MutableLiveData<String>()
    val currency2 = MutableLiveData<String>()
    val commonRates = MutableLiveData<DoubleArray>()
    val rateOnDay1 = MutableLiveData<String>()
    val rateOnDay2 = MutableLiveData<String>()
    val rateOnDay3 = MutableLiveData<String>()
    val day1Date = MutableLiveData<String>()
    val day2Date = MutableLiveData<String>()
    val day3Date = MutableLiveData<String>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val today: LocalDate = LocalDate.now()

    private val yesterday: LocalDate = today.minusDays(1)
    private val dayBeforeYesterday: LocalDate = today.minusDays(2)

    init {

        day1Date.value = today.toString()
        day2Date.value = yesterday.toString()
        day3Date.value = dayBeforeYesterday.toString()
        // Fetch exchange rates from the server
        fetchRates()
    }

    private fun fetchRates() {
        viewModelScope.launch {
            try {
                if (Utils.isInternetAvailable(appContext))
                    when(val response = getCurrencyRatesUseCase.getCurrenciesRateByDate(yesterday.toString())){
                        is Result.Success ->{
                            val rateAgainstBase =   response.data.rates[currency2.value]!! /response.data.rates[currency1.value]!!
                            rateOnDay2.postValue(String.format("%.3f",  rateAgainstBase))


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
        viewModelScope.launch {
            try {
                if (Utils.isInternetAvailable(appContext))
                    when(val response = getCurrencyRatesUseCase.getCurrenciesRateByDate(dayBeforeYesterday.toString())){
                        is Result.Success ->{
                            val rateAgainstBase =   response.data.rates[currency2.value]!! / response.data.rates[currency1.value]!!
                            rateOnDay3.postValue(String.format("%.3f",  rateAgainstBase))

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
}