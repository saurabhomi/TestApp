package com.mvi.base

import com.google.gson.JsonParseException
import com.mvi.common.constants.Constants.Companion.DATA_NOT_FOUND_ERROR
import com.mvi.common.constants.Constants.Companion.DATA_PARSING_ERROR
import com.mvi.common.constants.Constants.Companion.NETWORK_ERROR
import com.mvi.common.constants.Constants.Companion.UNKNOWN_ERROR
import java.io.IOException

abstract class BaseRepository {
    protected suspend fun <T, M> fetchAPiData(
        resource: suspend () -> T, mapper: Mapper<T, M>
    ): Resource<M> {

        try {
            // Emit data
            val data = resource()

            return Resource.Success(mapper.from(data))


        } catch (ex: IOException) {
            return Resource.Error(
                MovieRemoteDataSourceException.NetworkException(
                    "$NETWORK_ERROR ${ex.message}"
                )
            )

        } catch (ex: JsonParseException) {
            return Resource.Error(
                MovieRemoteDataSourceException.DataParseException(
                    "$DATA_PARSING_ERROR ${ex.message}"
                )
            )

        } catch (ex: NullPointerException) {
            return Resource.Error(
                MovieRemoteDataSourceException.DataNotFoundException(
                    "$DATA_NOT_FOUND_ERROR ${ex.message}"
                )
            )

        } catch (ex: Exception) {
            return Resource.Error(
                MovieRemoteDataSourceException.UnknownException(
                    "$UNKNOWN_ERROR ${ex.message}"
                )
            )

        }
    }
}