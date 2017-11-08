package com.nenton.schools.di.components;

import com.nenton.schools.di.modules.ModelModule;
import com.nenton.schools.mvp.model.AbstractModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
