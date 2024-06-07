package com.mvi.base

sealed class MovieRemoteDataSourceException(message: String) :
    Exception(message) {
    class NetworkException(message: String) :
        MovieRemoteDataSourceException(message)

    class DataParseException(message: String) :
        MovieRemoteDataSourceException(message)

    class DataNotFoundException(message: String) :
        MovieRemoteDataSourceException(message)

    class UnknownException(message: String) :
        MovieRemoteDataSourceException(message)
}