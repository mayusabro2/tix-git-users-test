package id.tix.gitusers.features.domain.use_case

import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.tix.gitusers.core.network.Resource
import id.tix.gitusers.features.domain.entity.model.User

import id.tix.gitusers.features.domain.repository.UserRepository


import kotlinx.coroutines.flow.Flow

class GetUsers(
    private val repository : UserRepository
) {


    operator fun invoke(): Flow<PagingData<User>> {
        return repository.getUsers()
    }

}