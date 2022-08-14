package com.mehyo.postsapp.di

import com.mehyo.postsapp.network.PostsAPI
import com.mehyo.postsapp.repository.NetworkRepository
import com.mehyo.postsapp.ui.viewmodel.HomeViewModel
import com.mehyo.postsapp.util.Constants
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network modules:
 * created network api using retrofit variable.
 * then building retrofit variable.
 * And finally created singleton values
 * to inject in constructors the when needed.
 */

val networkModule = module {
    fun createNetworkApi(retrofit: Retrofit) = retrofit.create(PostsAPI::class.java)

    fun retrofitBuilder() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

    single { createNetworkApi(retrofit = get()) }
    single { retrofitBuilder() }
}

/**
 * Repository modules:
 * created a singleton value
 * to inject in constructors the when needed.
 */
val repositoryModule = module {
    single { NetworkRepository(api = get()) }
}

/**
 * ViewModel modules:
 * created a singleton value
 * to inject viewModel objects the when needed.
 */
val viewModelModule = module {
    viewModel { HomeViewModel(networkRepository = get()) }
}