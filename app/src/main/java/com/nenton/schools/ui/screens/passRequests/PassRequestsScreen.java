package com.nenton.schools.ui.screens.passRequests;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.PassRequestsModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;

import dagger.Provides;
import mortar.MortarScope;

/**
 * Created by serge on 22.11.2017.
 */
@Screen(R.layout.screen_pass_requests)
public class PassRequestsScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerPassRequestsScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(PassRequestsScreen.class)
        PassRequestsPresenter providesPassRequestsPresenter() {
            return new PassRequestsPresenter();
        }

        @Provides
        @DaggerScope(PassRequestsScreen.class)
        PassRequestsModel providesPassRequestsModel() {
            return new PassRequestsModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(PassRequestsScreen.class)
    public interface Component {
        void inject(PassRequestsPresenter presenter);

        void inject(PassRequestsView view);

        RootPresenter getRootPresenter();
    }

    public class PassRequestsPresenter extends AbstractPresenter<PassRequestsView, PassRequestsModel> {

        @Override
        protected void initActivityBarBuilder() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
