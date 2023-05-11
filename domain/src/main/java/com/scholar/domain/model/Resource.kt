package com.scholar.domain.model

sealed class Resource<T>(val data: T? = null, val message: ErrorException? = null) {

    class Success<T>(data: T?) : Resource<T>(data)

    class Error<T>(message: ErrorException?, data: T? = null) : Resource<T>(data, message)
}

