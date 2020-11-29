package com.hanna.clearscore.application.test.di.components

import com.hanna.clearscore.application.test.di.modules.AppModule
import com.hanna.clearscore.application.test.di.modules.NetModule
import com.hanna.clearscore.application.test.di.modules.RepositoryModule
import com.hanna.clearscore.application.test.repository.CreditScoreRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AppModule::class, NetModule::class,
        RepositoryModule::class]
)
interface NetComponent {

    val comicsRepository: CreditScoreRepository
}
