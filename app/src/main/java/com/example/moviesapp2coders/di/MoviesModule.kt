package com.example.moviesapp2coders.di

import android.content.Context
import androidx.room.Room
import com.example.moviesapp2coders.BuildConfig
import com.example.moviesapp2coders.local.MoviesDatabase
import com.example.moviesapp2coders.remote.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MoviesModule {

    @Provides
    @Singleton
    internal fun provideBeerDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "MoviesDb"
        ).build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val url = chain.request().url
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }
        return OkHttpClient().newBuilder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): MoviesApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(MoviesApi.MOVIES_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}
