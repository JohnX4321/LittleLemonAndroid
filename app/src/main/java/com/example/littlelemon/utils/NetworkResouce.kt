package com.example.littlelemon.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException

inline fun <NetworkType,DbType> networkBoundResource(
    crossinline query: () -> Flow<DbType>,
    crossinline fetch: suspend () -> NetworkType,
    crossinline saveFetchedResult: suspend (NetworkType) -> Unit,
    crossinline shouldFetch: (DbType) -> Boolean = {true}
) = flow {
    val data = query().first()
    val f = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            saveFetchedResult(fetch())
            query().map { Resource.Success(it) }
        } catch (e: IOException) {
            query().map { Resource.Error("An unexpected error ${e.message}",it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(f)
}