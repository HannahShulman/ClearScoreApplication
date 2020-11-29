package com.hanna.clearscore.application.test.di

import com.hanna.clearscore.application.test.MyApp
import com.hanna.clearscore.application.test.di.components.DaggerApplicationComponent
import com.hanna.clearscore.application.test.ui.CreditScoreDisplayFragment


object DaggerInjectHelper {

    fun inject(fragment: CreditScoreDisplayFragment) {
        DaggerApplicationComponent.builder()
            .netComponent((fragment.context?.applicationContext as MyApp).netComponent)
            .build()
            .inject(fragment)
    }

}