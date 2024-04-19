package com.example.pokemonbook.utils

open class OnceEvent<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    //取過就無法再取
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    //無視取過直接拿
    fun peekContent(): T = content
}