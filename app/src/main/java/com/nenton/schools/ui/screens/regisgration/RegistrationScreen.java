package com.nenton.schools.ui.screens.regisgration;

import android.os.Bundle;

import com.nenton.schools.R;
import com.nenton.schools.data.storage.dto.UserDto;
import com.nenton.schools.data.storage.realm.UserRealm;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.RegistrationModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import rx.Subscriber;

/**
 * Created by serge on 08.11.2017.
 */

@Screen(R.layout.second_layout)
public class RegistrationScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerRegistrationScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(RegistrationScreen.class)
        RegistrationModel provideRegistrationModel() {
            return new RegistrationModel();
        }

        @Provides
        @DaggerScope(RegistrationScreen.class)
        RegistrationPresenter provideMainPresenter() {
            return new RegistrationPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(RegistrationScreen.class)
    public interface Component {
        void inject(RegistrationPresenter presenter);

        void inject(RegistrationView view);

        RootPresenter getRootPresenter();
    }

    public class RegistrationPresenter extends AbstractPresenter<RegistrationView, RegistrationModel> {

        @Override
        protected void initActionBar() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((RegistrationScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            mCompSubs.add(mModel.getUser().subscribe(new RealmSubscriber()));
        }

        private class RealmSubscriber extends Subscriber<UserRealm> {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (getRootView() != null) {
                    getRootView().showError(e);
                }
            }

            @Override
            public void onNext(UserRealm user) {
                if (getRootView() != null && getView() != null) {
                    getView().initView(user);
                }
            }
        }
    }
}
