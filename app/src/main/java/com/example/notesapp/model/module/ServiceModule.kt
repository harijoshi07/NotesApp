package com.example.notesapp.model.module

import com.example.notesapp.model.impl.AccountServiceImpl
import com.example.notesapp.model.impl.StorageServiceImpl
import com.example.notesapp.model.service.AccountService
import com.example.notesapp.model.service.StorageService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}