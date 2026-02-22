package com.green.robot.rickandmorty.di

import android.app.Application
import com.green.robot.rickandmorty.presenter.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@AppScope
@Component(
    modules = [
        AppModule::class,
        DataBaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UtilsModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope
