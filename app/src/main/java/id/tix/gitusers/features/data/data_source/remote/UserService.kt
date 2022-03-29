package id.tix.gitusers.features.data.data_source.remote

import id.tix.gitusers.features.data.data_source.remote.UsersPagingSource.Companion.PER_PAGE
import id.tix.gitusers.features.domain.entity.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface UserService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since : Int, @Query("per_page") perPage : Int = PER_PAGE
    ): List<User>
    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username : String
    ): User

}