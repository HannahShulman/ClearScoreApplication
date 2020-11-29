package com.hanna.clearscore.application.test

import android.app.Application
import com.hanna.clearscore.application.test.di.components.DaggerNetComponent
import com.hanna.clearscore.application.test.di.components.NetComponent
import com.hanna.clearscore.application.test.di.modules.AppModule
import com.hanna.clearscore.application.test.di.modules.NetModule

class MyApp : Application() {

    lateinit var netComponent: NetComponent

    override fun onCreate() {
        super.onCreate()
        //init the graph
        netComponent = DaggerNetComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build()
    }
}