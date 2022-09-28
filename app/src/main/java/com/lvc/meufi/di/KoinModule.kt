package com.lvc.meufi.di

import com.lvc.meufi.add.AddFiiViewModel
import com.lvc.meufi.data_scripts.ScripManager
import com.lvc.meufi.home.HomeViewModel
import com.lvc.meufi.persistence.FiiDividendRepository
import com.lvc.meufi.persistence.local.FiiDatabase
import com.lvc.meufi.persistence.local.LocalDataRegister
import com.lvc.meufi.persistence.remote.SkrapeHandler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { LocalDataRegister(androidContext()) }

    factory { SkrapeHandler() }

    single {
        FiiDatabase.createDatabase(androidContext()).createFiiDividendDAO()
    }

    single {
        FiiDatabase.createDatabase(androidContext()).createFiiDAO()
    }

    single {
        FiiDatabase.createDatabase(androidContext()).createMyFiiDAO()
    }

    single { FiiDividendRepository(get(), get(), get(), get() ) }

    single { ScripManager(get(), get(), get() ) }

    viewModel { HomeViewModel(get()) }

    viewModel { AddFiiViewModel() }
}