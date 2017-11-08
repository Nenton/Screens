package com.nenton.schools.di.modules;

import com.nenton.schools.di.sqopes.AuthScope;
import com.nenton.schools.mvp.presenters.AuthPresenter;

import dagger.Provides;

@dagger.Module
public class AuthModule {
    @Provides
    @AuthScope
    public AuthPresenter provideAuthPresenter() {
        return new AuthPresenter();
    }
}
