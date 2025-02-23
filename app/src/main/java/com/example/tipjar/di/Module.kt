package com.example.tipjar.di

import androidx.room.Room
import com.example.tipjar.data.db.TIP_DB
import com.example.tipjar.data.db.TipDatabase
import com.example.tipjar.data.repository.DataStoreRepositoryImpl
import com.example.tipjar.data.repository.TipRepositoryImpl
import com.example.tipjar.domain.repository.DataStoreRepository
import com.example.tipjar.domain.repository.TipRepository
import com.example.tipjar.domain.usecase.JSONAssetToObjectListUseCase
import com.example.tipjar.domain.usecase.tip.CreateTipUseCase
import com.example.tipjar.domain.usecase.tip.RemoveTipUseCase
import com.example.tipjar.domain.usecase.tip.SearchTipUseCase
import com.example.tipjar.viewmodel.NewPaymentViewModel
import com.example.tipjar.viewmodel.TipHistoryViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

// inject feature
fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            databaseModule,
            repositoryModule,
            dispatcherModule,
            useCaseModule,
            viewModelModule,
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

val dispatcherModule = module {
    single(named("IODispatcher")) { Dispatchers.IO }
}

val useCaseModule = module {
    factory { CreateTipUseCase(get(named("IODispatcher")), get()) }
    factory { RemoveTipUseCase(get(named("IODispatcher")), get()) }
    factory { SearchTipUseCase(get(named("IODispatcher")), get()) }
    factory { JSONAssetToObjectListUseCase(get(named("IODispatcher"))) }
}

val viewModelModule = module {
    viewModel { TipHistoryViewModel(get(), get(), get()) }
    viewModel { NewPaymentViewModel(get(), get(), get()) }
}