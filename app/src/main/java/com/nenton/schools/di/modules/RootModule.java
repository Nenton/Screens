package com.nenton.schools.di.modules;

import com.nenton.schools.di.sqopes.RootScope;
import com.nenton.schools.mvp.presenters.RootPresenter;

import dagger.Provides;

@dagger.Module
public class RootModule {
    @Provides
    @RootScope
    public RootPresenter provideRootPresenter() {
        return new RootPresenter();
    }
}
