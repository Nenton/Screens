package com.nenton.schools.di.components;

import android.content.Context;


import com.nenton.schools.di.modules.AppModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
