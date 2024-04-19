package com.example.pokemonbook.model


sealed class ApiResult<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Failed<T>(data: T?, message: String?) : ApiResult<T>(data, message)
}