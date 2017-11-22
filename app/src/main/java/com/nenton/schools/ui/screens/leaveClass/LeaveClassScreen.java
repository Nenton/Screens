package com.nenton.schools.ui.screens.leaveClass;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.LeaveClassModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;

import dagger.Provides;
import mortar.MortarScope;

/**
 * Created by serge on 22.11.2017.
 */
@Screen(R.layout.screen_leave_class)
public class LeaveClassScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerLeaveClassScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(LeaveClassScreen.class)
        LeaveClassModel provideLeaveClassModel() {
            return new LeaveClassModel();
        }

        @Provides
        @DaggerScope(LeaveClassScreen.class)
        LeaveClassPresenter provideLeaveClassPresenter() {
            return new LeaveClassPresenter();
        }

    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(LeaveClassScreen.class)
    public interface Component {
        void inject(LeaveClassPresenter presenter);

        void inject(LeaveClassView view);

        RootPresenter getRootPresenter();
    }

    public class LeaveClassPresenter extends AbstractPresenter<LeaveClassView, LeaveClassModel> {

        @Override
        protected void initActivityBarBuilder() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }

}
