package com.nenton.schools.ui.screens.auth;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.AuthModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.IAuthPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;
import com.nenton.schools.ui.screens.main.MainScreen;
import com.nenton.schools.ui.screens.regisgration.RegistrationScreen;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;

/**
 * Created by serge on 07.11.2017.
 */

@Screen(R.layout.screen_auth)
public class AuthScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerAuthScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(AuthScreen.class)
        AuthModel provideAuthModel() {
            return new AuthModel();
        }

        @Provides
        @DaggerScope(AuthScreen.class)
        AuthPresenter provideAuthPresenter() {
            return new AuthPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(AuthScreen.class)
    public interface Component {
        void inject(AuthPresenter presenter);

        void inject(AuthView view);

        RootPresenter getRootPresenter();
    }

    public class AuthPresenter extends AbstractPresenter<AuthView, AuthModel> implements IAuthPresenter {

        public int STATE_NEW_ACCOUNT = 1456;
        public int STATE_EXISTING_ACCOUNT = 1455;
        private int stateScreen = STATE_EXISTING_ACCOUNT;

        @Override
        protected void initActivityBarBuilder() {
            mRootPresenter.newRootActivityBarBuilder()
                    .setShowBottomNav(false)
                    .build();
        }

        public void changeState(int state) {
            stateScreen = state;
            if (getView() != null) {
                if (stateScreen == STATE_EXISTING_ACCOUNT) {
                    getView().showExistingAccountState();
                } else if (stateScreen == STATE_NEW_ACCOUNT) {
                    getView().showNewAccountState();
                }
            }
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView();
            }
        }

        public void clickCreateUser(String email, String pass, String fullName) {
            if (getView() != null && getRootView() != null) {
                mCompSubs.add(
                        mModel.createUser(email, pass, fullName)
                                .subscribe(firebaseUser -> {
                                    getRootView().showMessage("Complete");
                                    mModel.saveUserInfo();
                                    Flow.get(getView()).set(new RegistrationScreen());
                                }, throwable -> {
                                    if (throwable instanceof FirebaseAuthUserCollisionException) {
                                        getRootView().showMessage("Collision exception");
                                    } else {
                                        getRootView().showMessage(throwable.getMessage());
                                    }
                                })
                );
            }
        }


        public void clickLogin(String email, String pass) {
            if (getView() != null && getRootView() != null) {
                mCompSubs.add(
                        mModel.loginUser(email, pass)
                                .subscribe(firebaseUser -> {
                                    getRootView().showMessage("Complete");
                                    mModel.saveUserInfo();
                                    mCompSubs.add(
                                            mModel.checkCompleteRegistration().subscribe(aBoolean -> {
                                                Flow.get(getView()).set(aBoolean ? new MainScreen() : new RegistrationScreen());
                                            }, throwable -> {
                                                getRootView().showError(throwable);
                                            }));
                                }, throwable -> {
                                    getRootView().showMessage(throwable.getMessage());
                                })
                );
            }
        }
    }
}
