package com.nenton.schools.di.components;

import com.nenton.schools.data.managers.DataManager;
import com.nenton.schools.di.modules.LocalModule;
import com.nenton.schools.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = {LocalModule.class, NetworkModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
