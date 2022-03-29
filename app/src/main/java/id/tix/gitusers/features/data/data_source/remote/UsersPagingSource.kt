package id.tix.gitusers.features.data.data_source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.tix.gitusers.core.data_source.BasePagingSource
import id.tix.gitusers.core.network.FailureStatus
import id.tix.gitusers.core.network.Resource
import id.tix.gitusers.core.network.safeApiCall
import id.tix.gitusers.features.domain.entity.model.User
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject


class UsersPagingSource @Inject constructor(private val service: UserService) : BasePagingSource<UserService, User>() {
    companion object {
        const val PER_PAGE = 10
    }

    override suspend fun getData(params: LoadParams<Int>): List<User> =
        service.getUsers(since = params.key ?: STARTING_PAGE_INDEX)

    override suspend fun getNextKey(data: List<User>): Int = (data.lastOrNull()?.id?:0).toInt() + 1



}