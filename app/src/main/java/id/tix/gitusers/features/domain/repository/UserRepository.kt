package id.tix.gitusers.features.domain.repository

import androidx.paging.PagingData
import id.tix.gitusers.core.network.Resource
import id.tix.gitusers.features.domain.entity.model.User


import kotlinx.coroutines.flow.Flow
 interface UserRepository {

     fun getUsers(): Flow<PagingData<User>>
     fun getUser(username : String): Flow<Resource<User>>

 }


