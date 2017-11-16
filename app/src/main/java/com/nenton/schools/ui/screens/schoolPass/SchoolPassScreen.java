package com.nenton.schools.ui.screens.schoolPass;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.SchoolPassModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;
import com.nenton.schools.ui.custom_views.CustomChronometer;

import java.util.Date;

import dagger.Provides;
import mortar.MortarScope;

/**
 * Created by serge on 15.11.2017.
 */
@Screen(R.layout.screen_school_pass)
public class SchoolPassScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerSchoolPassScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(SchoolPassScreen.class)
        SchoolPassModel provideSchoolPassModel() {
            return new SchoolPassModel();
        }

        @Provides
        @DaggerScope(SchoolPassScreen.class)
        SchoolPassPresenter provideSchoolPassPresenter() {
            return new SchoolPassPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(SchoolPassScreen.class)
    public interface Component {
        void inject(SchoolPassPresenter presenter);

        void inject(SchoolPassView view);

        RootPresenter getRootPresenter();
    }

    public class SchoolPassPresenter extends AbstractPresenter<SchoolPassView, SchoolPassModel> {

        @Override
        protected void initActivityBarBuilder() {
            mRootPresenter.newRootActivityBarBuilder()
                    .setShowBottomNav(true)
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((SchoolPassScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView();
            }
        }

        public void changeSwitch(int switchId) {
            if (getView() != null) {
                switch (switchId) {
                    case R.id.pass_restroom_sb:
                        getView().showOnlyRestroom();
                        break;
                    case R.id.pass_main_office_sb:
                        getView().showOnlyMainOffice();
                        break;
                }
            }
        }

        public void clickOnChalkboard() {
            if (getRootView() != null && getView() != null) {
                CustomChronometer chronometer = getView().getChronometer();
                if (chronometer.isRunning()) {
                    Date date = new Date(SystemClock.elapsedRealtime() - chronometer.getBase());
                    getRootView().showMessage(String.valueOf(date.getMinutes()) + ":" + String.valueOf(date.getSeconds()));
                } else {
                    chronometer.start();
                }
            }
        }
    }
}
