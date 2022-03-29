package id.tix.gitusers.features.presentation.users


import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.tix.gitusers.features.domain.use_case.UserUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UserViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    val userId = MutableStateFlow("")
    val userDetail = userId.flatMapLatest {
        userUseCases.getUser(it)
    }

    fun getUsers() = userUseCases.getUsers()

}