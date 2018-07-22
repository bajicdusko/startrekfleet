package com.bajicdusko.androiddomain

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
data class ResponseWrapper<T>(val data: T? = null, val error: Throwable? = null)

fun <T> wrappedData(fn: () -> T) = ResponseWrapper<T>(data = fn())
fun <T> wrappedError(fn: () -> Throwable) = ResponseWrapper<T>(
    error = fn())

