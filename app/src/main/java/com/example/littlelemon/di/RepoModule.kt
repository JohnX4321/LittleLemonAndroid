package com.example.littlelemon.di

import com.example.littlelemon.models.MenuRepo
import com.example.littlelemon.models.MenuRepoImpl
import com.example.littlelemon.models.SharedPrefs
import com.example.littlelemon.models.SharedPrefsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun bindsMenuRepo(menuRepoImpl: MenuRepoImpl): MenuRepo

    @Binds
    @Singleton
    abstract fun bindsSharedPrefsRepo(sharedPrefs: SharedPrefs): SharedPrefsRepo
}