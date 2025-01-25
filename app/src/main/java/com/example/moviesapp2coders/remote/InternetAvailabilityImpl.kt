package com.example.moviesapp2coders.remote

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

internal class InternetAvailabilityImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : InternetAvailability {

    //Check internet connectivity
    override suspend fun hasInternet(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)
    }
}
