package id.tix.gitusers.features.data.data_source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.gson.JsonObject
import id.tix.gitusers.core.data_source.BaseRemoteDataSource
import id.tix.gitusers.core.network.safeApiCall
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val service: UserService) : BaseRemoteDataSource() {

    fun getUsers() = Pager(config = PagingConfig(pageSize = UsersPagingSource.PER_PAGE, enablePlaceholders = false), pagingSourceFactory = {UsersPagingSource(service = service)})

    suspend fun getUser(username : String) = safeApiCall {
        service.getUser(username)
    }
}