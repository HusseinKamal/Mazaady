package com.hussein.mazaady.di

import com.hussein.mazaady.data.remote.ApiService
import com.hussein.mazaady.domain.repository.CategoryRepositoryImpl
import com.hussein.mazaady.presentation.viewmodel.MainViewModel
import com.hussein.mazaady.domain.repository.CategoryRepository
import org.koin.dsl.module

val appModule = module {
    single { ApiService() }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    factory { MainViewModel(get()) }
}