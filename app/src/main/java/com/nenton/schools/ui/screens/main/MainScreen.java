package com.nenton.schools.ui.screens.main;

import android.os.Bundle;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.MainModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.IMainPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;
import com.nenton.schools.ui.screens.schoolPass.SchoolPassScreen;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;

/**
 * Created by serge on 08.11.2017.
 */
@Screen(R.layout.screen_main)
public class MainScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerMainScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(MainScreen.class)
        MainModel provideMainModel() {
            return new MainModel();
        }

        @Provides
        @DaggerScope(MainScreen.class)
        MainScreen.MainPresenter provideMainPresenter() {
            return new MainScreen.MainPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(MainScreen.class)
    public interface Component {
        void inject(MainPresenter presenter);

        void inject(MainView view);

        RootPresenter getRootPresenter();
    }

    public class MainPresenter extends AbstractPresenter<MainView, MainModel> implements IMainPresenter {

        @Override
        protected void initActivityBarBuilder() {
            mRootPresenter.newRootActivityBarBuilder()
                    .setShowBottomNav(false)
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((MainScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView();
            }
        }

        public void clickOnPurchase() {
            if (getView() != null) {
                Flow.get(getView()).set(new SchoolPassScreen());
            }
        }
    }
}
