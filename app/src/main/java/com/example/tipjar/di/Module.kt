package com.example.tipjar.di

import androidx.room.Room
import com.example.tipjar.data.db.TIP_DB
import com.example.tipjar.data.db.TipDatabase
import com.example.tipjar.data.repository.TipRepositoryImpl
import com.example.tipjar.domain.repository.TipRepository
import com.example.tipjar.domain.usecase.CreateTipUseCase
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
}

val useCaseModule = module {
    factory { CreateTipUseCase(get()) }
//    factory { GetTipUseCase(get()) }
//    factory { RemoveTipUseCase(get()) }
//    factory { SearchTipUseCase(get()) }
}

val viewModelModule = module {
    viewModel { TipHistoryViewModel(get()) }
}

val validationModule = module {
//    factory { FormValidation() }
}