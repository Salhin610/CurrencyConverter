package com.task.currencyconverter.data.model

data class CurrencyRatesResponseModel(val success: Boolean, val rates: HashMap<String, Double>)
