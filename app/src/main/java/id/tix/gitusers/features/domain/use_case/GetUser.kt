package id.tix.gitusers.features.domain.use_case

import id.tix.gitusers.core.network.Resource
import id.tix.gitusers.features.domain.entity.model.User

import id.tix.gitusers.features.domain.repository.UserRepository


import kotlinx.coroutines.flow.Flow

class GetUser(
    private val repository : UserRepository
) {


    operator fun invoke(username : String): Flow<Resource<User>> {
        return repository.getUser(username)
    }
}