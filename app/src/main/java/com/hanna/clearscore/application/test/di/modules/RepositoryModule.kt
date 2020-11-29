package com.hanna.clearscore.application.test.di.modules


import com.hanna.clearscore.application.test.repository.CreditScoreRepository
import com.hanna.clearscore.application.test.repository.CreditScoreRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun creditScoreRepository(repository: CreditScoreRepositoryImpl): CreditScoreRepository
}