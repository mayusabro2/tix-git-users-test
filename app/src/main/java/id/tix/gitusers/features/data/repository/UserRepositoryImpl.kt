package id.tix.gitusers.features.data.repository

import id.tix.gitusers.core.network.Resource
import id.tix.gitusers.core.network.performGetOperation
import id.tix.gitusers.features.data.data_source.remote.UserRemoteDataSource
import id.tix.gitusers.features.data.data_source.remote.UsersPagingSource
import id.tix.gitusers.features.domain.entity.model.User
import id.tix.gitusers.features.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override fun getUsers() = remoteDataSource.getUsers().flow
    override fun getUser(username : String): Flow<Resource<User>> = performGetOperation { remoteDataSource.getUser(username) }
}


