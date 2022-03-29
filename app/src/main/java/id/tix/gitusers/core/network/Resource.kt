package id.tix.gitusers.core.network

import id.tix.gitusers.core.network.FailureStatus

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val failureStatus: FailureStatus? = null) {

  enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    EMPTY
  }

  companion object {
    fun <T> success(data: T): Resource<T> {
      return Resource(Status.SUCCESS, data, null)
    }

    fun <T> failure(status : FailureStatus = FailureStatus.OTHER, message: String, data: T? = null): Resource<T> {
      return Resource(Status.ERROR, data, message, status)
    }

    fun <T> loading(data: T? = null): Resource<T> {
      return Resource(Status.LOADING, data, null)
    }
    fun <T> empty(): Resource<T> {
      return Resource(Status.EMPTY, null, null)
    }
  }
}