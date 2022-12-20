package com.ssionii.bloodNote.di

import com.ssionii.bloodNote.data.NetworkService
import com.ssionii.bloodNote.data.repository.*
import com.ssionii.bloodNote.ui.main.EmptyViewModel
import com.ssionii.bloodNote.ui.main.MainViewModel
import com.ssionii.bloodNote.ui.signup.UserViewModel
import com.ssionii.bloodNote.ui.state.analysis.AnalysisViewModel
import com.ssionii.bloodNote.ui.state.report.ReportViewModel
import com.ssionii.bloodNote.ui.write.WriteBloodViewModel
import com.ssionii.bloodNote.util.scheduler.AndroidSchedulerProvider
import com.ssionii.bloodNote.util.scheduler.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val rxModule = module {
    //provide schedule provider
    factory<SchedulerProvider> { AndroidSchedulerProvider() }
}

val networkModule = module {
    single { NetworkService.create() }
}

val localModule = module {
    //SharedPreference
//    single { PreferenceManager() }
}

val factoryModule = module {
    factory<BloodSugarRepository> {
        BloodSugarRepositoryImpl(
            get()
        )
    }

    factory<BloodPressureRepository> {
        BloodPressureRepositoryImpl(
            get()
        )
    }

    factory<AnalysisRepository> {
        AnalysisRepositoryImpl(
            get()
        )
    }

    factory<UserRepository> {
        UserRepositoryImpl(
            get()
        )
    }
}

val viewModule = module {
    viewModel { MainViewModel() }
    viewModel { EmptyViewModel() }
    viewModel { WriteBloodViewModel(get(), get(), get()) }
    viewModel { ReportViewModel(get(), get(), get()) }
    viewModel { AnalysisViewModel(get(), get()) }
    viewModel { UserViewModel(get(), get()) }

}

val appModule = listOf(rxModule, networkModule, localModule, factoryModule, viewModule)