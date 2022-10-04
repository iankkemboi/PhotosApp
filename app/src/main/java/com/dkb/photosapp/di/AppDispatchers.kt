package com.dkb.photosapp.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchers(
    val Main: CoroutineDispatcher = Dispatchers.Main
)