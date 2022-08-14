package com.mehyo.postsapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PostsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@PostsApp)
            // use modules
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}