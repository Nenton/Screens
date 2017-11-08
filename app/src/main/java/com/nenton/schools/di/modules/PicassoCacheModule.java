package com.nenton.schools.di.modules;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.nenton.schools.di.sqopes.RootScope;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class PicassoCacheModule {

    @Provides
    @RootScope
    Picasso providePicasso(Context context) {
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(context);
        Picasso picasso = new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }
}
