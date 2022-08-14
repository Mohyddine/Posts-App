package com.mehyo.postsapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PostsApp : Application() {

    /**
     * Starting for DI.
     * then added the Koin Android logger
     * after that injected the Android context
     * and finally added all the modules
     */
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PostsApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}