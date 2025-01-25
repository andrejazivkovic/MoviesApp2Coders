package com.example.moviesapp2coders.remote

internal interface InternetAvailability {
    suspend fun hasInternet(): Boolean
}