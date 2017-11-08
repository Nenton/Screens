package com.nenton.schools.di.components;

import com.nenton.schools.di.modules.PicassoCacheModule;
import com.nenton.schools.di.sqopes.RootScope;
import com.squareup.picasso.Picasso;

import dagger.Component;

@Component(dependencies = AppComponent.class, modules = PicassoCacheModule.class)
@RootScope
public interface PicassoCacheComponent {
    Picasso getPicasso();
}
