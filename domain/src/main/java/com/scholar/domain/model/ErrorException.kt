package com.scholar.domain.model

sealed class ErrorException(message: String) : Exception(message) {
    class DynamicErrorException(message: String) : ErrorException(message)
    object NetworkErrorException : ErrorException("Please make sure that you are connected to network and try again")
    object UnknownErrorException : ErrorException("An unknown error has occurred, please try again.")
}
