package id.tix.gitusers.core.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.tix.gitusers.features.data.data_source.remote.UserService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
@InstallIn(SingletonComponent::class)
object NetworkServicesModule {

  @Provides
  @Singleton
  fun provideUserServices(retrofit: Retrofit): UserService {
    return retrofit.create(UserService::class.java)
  }
}