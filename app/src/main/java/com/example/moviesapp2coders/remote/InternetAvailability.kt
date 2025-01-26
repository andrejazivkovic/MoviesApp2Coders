package com.example.moviesapp2coders.remote

import kotlinx.coroutines.flow.Flow

internal interface InternetAvailability {
    val internetConnection: Flow<Status>
    fun hasInternet(): Boolean
}

internal enum class Status {
    Available, Unavailable, Losing, Lost
}