package com.nenton.schools.mvp.presenters;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.nenton.schools.data.storage.dto.ActivityResultDto;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.IRootView;
import com.nenton.schools.ui.activities.RootActivity;

import mortar.MortarScope;
import mortar.Presenter;
import mortar.bundler.BundleService;
import rx.subjects.PublishSubject;

public class RootPresenter extends Presenter<IRootView> {

    private PublishSubject<ActivityResultDto> mActivityResultSubject = PublishSubject.create();

    private static int DEFAULT_MODE = 0;
    private static int TAB_MODE = 1;

    @Override
    protected BundleService extractBundleService(IRootView view) {
        return BundleService.getBundleService((RootActivity) view);
    }

    public PublishSubject<ActivityResultDto> getActivityResultSubject() {
        return mActivityResultSubject;
    }

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        ((RootActivity.RootComponent) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
    }

    @Nullable
    public IRootView getRootView() {
        return getView();
    }

    public boolean checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissions, int requestCode) {
        boolean allGranted = true;
        allGranted = ((RootActivity) getView()).isAllGranted(permissions, allGranted);

        if (!allGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ((RootActivity) getView()).requestPermissions(permissions, requestCode);
            }
            return false;
        }
        return allGranted;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        mActivityResultSubject.onNext(new ActivityResultDto(requestCode, resultCode, intent));
    }

    public void onRequestPermissionResult(int requetCode, @NonNull String[] permissions, @NonNull int[] grantResult) {

    }

    public RootActivityBarBuilder newRootActivityBarBuilder() {
        return this.new RootActivityBarBuilder();
    }

    public class RootActivityBarBuilder {
        private boolean isShowBottomNav = false;

        public RootActivityBarBuilder setShowBottomNav(boolean showBottomNav) {
            this.isShowBottomNav = showBottomNav;
            return this;
        }

        public void build() {
            if (getRootView() != null) {
                RootActivity activity = (RootActivity) getRootView();
                activity.stateBottomNavView(isShowBottomNav);
            }
        }
    }
}