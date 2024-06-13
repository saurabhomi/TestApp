package com.mvi.network.base

import com.google.gson.JsonParseException
import com.mvi.common.Mapper
import com.mvi.common.Resource
import com.mvi.network.constant.NetworkConstants.Companion.DATA_NOT_FOUND_ERROR
import com.mvi.network.constant.NetworkConstants.Companion.DATA_PARSING_ERROR
import com.mvi.network.constant.NetworkConstants.Companion.NETWORK_ERROR
import com.mvi.network.constant.NetworkConstants.Companion.UNKNOWN_ERROR
import com.mvi.network.exception.MovieRemoteDataSourceException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {
    protected suspend fun <T, M> fetchAPiData(
        resource: suspend () -> Response<T>,
        mapper: Mapper<T, M>,
        dispatcher: CoroutineDispatcher
    ): Resource<M> {
        return try {
            withContext(dispatcher) {
                val data = resource()
                if (data.isSuccessful) {
                    Resource.Success(mapper.from(data.body()!!))
                } else {
                    Resource.Error(
                        MovieRemoteDataSourceException.DataNotFoundException(DATA_NOT_FOUND_ERROR)
                    )
                }
            }

        } catch (ex: IOException) {
            Resource.Error(
                MovieRemoteDataSourceException.NetworkException(
                    "$NETWORK_ERROR ${ex.message}"
                )
            )

        } catch (ex: JsonParseException) {
            Resource.Error(
                MovieRemoteDataSourceException.DataParseException(
                    "$DATA_PARSING_ERROR ${ex.message}"
                )
            )

        } catch (ex: NullPointerException) {
            Resource.Error(
                MovieRemoteDataSourceException.DataNotFoundException(
                    "$DATA_NOT_FOUND_ERROR ${ex.message}"
                )
            )

        } catch (ex: Exception) {
            Resource.Error(
                MovieRemoteDataSourceException.UnknownException(
                    "$UNKNOWN_ERROR ${ex.message}"
                )
            )

        }
    }
}