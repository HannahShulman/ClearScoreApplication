package com.hanna.clearscore.application.test.di.components;


import com.hanna.clearscore.application.test.di.scope.FragmentScoped;
import com.hanna.clearscore.application.test.ui.CreditScoreDisplayFragment;

import dagger.Component;

@FragmentScoped
@Component(dependencies = NetComponent.class)
public interface ApplicationComponent {

    void inject(CreditScoreDisplayFragment fragment);
}
