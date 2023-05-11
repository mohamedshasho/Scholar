package com.scholar.domain.model

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()

    class Error<T: Any>(val code: Int, val error: String?) : NetworkResult<T>()

    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}