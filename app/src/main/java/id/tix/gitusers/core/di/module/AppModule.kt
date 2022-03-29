package id.tix.gitusers.core.di.module


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.tix.gitusers.features.data.data_source.remote.UserRemoteDataSource
import id.tix.gitusers.features.data.data_source.remote.UserService
import id.tix.gitusers.features.data.data_source.remote.UsersPagingSource
import id.tix.gitusers.features.data.repository.UserRepositoryImpl
import id.tix.gitusers.features.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


  @Provides
  @Singleton
  fun provideUserRepository(rds: UserRemoteDataSource) : UserRepository{
    return UserRepositoryImpl(rds)
  }

  @Provides
  @Singleton
  fun provideUserRemoteDataSource(service : UserService) : UserRemoteDataSource{
    return UserRemoteDataSource(service)
  }

}