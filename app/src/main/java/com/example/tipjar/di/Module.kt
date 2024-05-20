package com.example.tipjar.di

import androidx.room.Room
import com.example.tipjar.data.db.TIP_DB
import com.example.tipjar.data.db.TipDatabase
import com.example.tipjar.data.repository.DataStoreRepositoryImpl
import com.example.tipjar.data.repository.TipRepositoryImpl
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.repository.TipRepository
import com.example.tipjar.domain.usecase.tip.CreateTipUseCase
import com.example.tipjar.domain.usecase.tip.RemoveTipUseCase
import com.example.tipjar.domain.usecase.tip.SearchTipUseCase
import com.example.tipjar.viewmodel.NewPaymentViewModel
import com.example.tipjar.viewmodel.TipHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            databaseModule,
            repositoryModule,
            useCaseModule,
            viewModelModule,
            validationModule
        )
    )
}
val databaseModule = module {
    single { Room.databaseBuilder(get(), TipDatabase::class.java, TIP_DB).build() }
    single { get<TipDatabase>().getTipHistoryDao() }
}

val repositoryModule = module {
    single<TipRepository> { TipRepositoryImpl(get()) }
    single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { CreateTipUseCase(get()) }
    factory { RemoveTipUseCase(get()) }
    factory { SearchTipUseCase(get()) }
}

val viewModelModule = module {
    viewModel { TipHistoryViewModel(get(), get(), get()) }
    viewModel { NewPaymentViewModel(get(), get()) }
}

val validationModule = module {
//    factory { FormValidation() }
}