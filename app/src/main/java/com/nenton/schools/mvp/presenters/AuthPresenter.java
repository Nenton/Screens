package com.nenton.schools.mvp.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nenton.schools.data.storage.dto.ActivityResultDto;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.IAuthView;
import com.nenton.schools.mvp.views.IRootView;
import com.nenton.schools.ui.activities.AuthActivity;
import com.nenton.schools.ui.activities.RootActivity;

import mortar.MortarScope;
import mortar.Presenter;
import mortar.bundler.BundleService;
import rx.subjects.PublishSubject;

/**
 * Created by serge on 07.11.2017.
 */

public class AuthPresenter extends Presenter<IAuthView> {

    private PublishSubject<ActivityResultDto> mActivityResultSubject = PublishSubject.create();

    @Override
    protected BundleService extractBundleService(IAuthView view) {
        return BundleService.getBundleService((AuthActivity) view);
    }

    public PublishSubject<ActivityResultDto> getActivityResultSubject() {
        return mActivityResultSubject;
    }

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        ((AuthActivity.AuthComponent) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
    }

    @Nullable
    public IAuthView getRootView() {
        return getView();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mActivityResultSubject.onNext(new ActivityResultDto(requestCode, resultCode, intent));
    }

    public void onRequestPermissionResult(int requetCode, @NonNull String[] permissions, @NonNull int[] grantResult) {

    }
}
