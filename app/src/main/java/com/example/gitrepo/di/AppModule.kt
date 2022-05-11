package com.example.gitrepo.di

import com.example.gitrepo.common.Constants
import com.example.gitrepo.data.remote.RepoApi
import com.example.gitrepo.data.repository.RepoRepositoryImpl
import com.example.gitrepo.domain.repository.RepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRepoApi(): RepoApi{
        return Retrofit.Builder()
            .baseUrl(Constants.repoApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RepoApi::class.java)

    }

    @Provides
    @Singleton
    fun provideCoinRepository(api : RepoApi): RepoRepository {
        return RepoRepositoryImpl(api)
    }
}