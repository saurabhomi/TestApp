package com.mvi.base

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}