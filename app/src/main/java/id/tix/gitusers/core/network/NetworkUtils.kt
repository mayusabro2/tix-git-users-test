package id.tix.gitusers.core.network

import id.tix.gitusers.core.network.FailureStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
  return withContext(Dispatchers.IO) {
    try {
      val apiResponse: T = apiCall.invoke()

      if (apiResponse is List<*>) {
        if (apiResponse.isEmpty()) {
          Resource.empty()
        } else {
          Resource.success(apiResponse)
        }
      } else {
        Resource.success(apiResponse)
      }
    } catch (throwable: Throwable) {
      throwable.printStackTrace()
      when (throwable) {
        is HttpException -> {
          when {
              throwable.code() == 401 -> {
                  Resource.failure(FailureStatus.TOKEN_EXPIRED, "Unauthorized")
              }
              throwable.code() == 403 -> {
                  Resource.failure(FailureStatus.SERVER_SIDE_EXCEPTION, "Server limit reached")
              }
              else -> {
                  Resource.failure(FailureStatus.OTHER, "Something wrong")
              }
          }
        }

        is UnknownHostException -> {
          Resource.failure(FailureStatus.NO_INTERNET,  "No Internet")
        }

        else -> {
          Resource.failure(FailureStatus.OTHER, "Something wrong")
        }
      }
    }
  }
}

fun <A> performGetOperation(networkCall: suspend () -> Resource<A>)  =
  flow{
    emit(Resource.loading())
    val responseStatus = networkCall()

    //Cache to database if response is successful
    if (responseStatus.status == Resource.Status.SUCCESS) {
      responseStatus.data?.apply {
        emit(Resource.success(this))
      }

    } else if (responseStatus.status == Resource.Status.ERROR) {
      emit(
        Resource.failure(
          responseStatus.failureStatus ?: FailureStatus.OTHER,
          responseStatus.message ?: ""
        )
      )
    }
  }.flowOn(Dispatchers.IO)

fun <T> performPostOperation(saveCallResult: suspend () -> Unit,
                               networkCall: suspend () -> Unit
) =
  flow {
    emit(Resource.loading())
    emit(Resource.success(saveCallResult()))
    networkCall()
  }.flowOn(Dispatchers.IO)


