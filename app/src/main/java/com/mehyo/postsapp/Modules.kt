package com.mehyo.postsapp

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Network modules
val networkModule = module {
    //creating network api using retrofit variable
    fun createNetworkApi(retrofit: Retrofit) = retrofit.create(PostsAPI::class.java)

    //Building retrofit variable
    fun retrofitBuilder() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

    single { createNetworkApi(retrofit = get()) }
    single { retrofitBuilder() }
}

//Repository modules
val repositoryModule = module {
    single { NetworkRepository(api = get()) }
}

//ViewModel modules
val viewModelModule = module {
    viewModel { HomeViewModel(networkRepository = get()) }
}