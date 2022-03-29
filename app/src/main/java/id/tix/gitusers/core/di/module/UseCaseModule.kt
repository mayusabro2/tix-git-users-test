package id.tix.gitusers.core.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.tix.gitusers.features.domain.repository.UserRepository
import id.tix.gitusers.features.domain.use_case.GetUser
import id.tix.gitusers.features.domain.use_case.GetUsers
import id.tix.gitusers.features.domain.use_case.UserUseCases
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideUserUseCases(fishRepository: UserRepository) = UserUseCases(GetUsers(fishRepository), GetUser(fishRepository))

}