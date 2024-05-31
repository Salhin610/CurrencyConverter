package com.task.currencyconverter.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception? = null) : Result<Nothing>()
}
