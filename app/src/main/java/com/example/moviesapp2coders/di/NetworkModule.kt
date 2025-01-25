package com.example.moviesapp2coders.di

import com.example.moviesapp2coders.remote.InternetAvailability
import com.example.moviesapp2coders.remote.InternetAvailabilityImpl
import com.example.moviesapp2coders.remote.Repository
import com.example.moviesapp2coders.remote.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetworkChecker(
        networkCheckerImpl: InternetAvailabilityImpl
    ): InternetAvailability

    @Binds
    @Singleton
    abstract fun repository(repositoryImpl: RepositoryImpl) : Repository
}
