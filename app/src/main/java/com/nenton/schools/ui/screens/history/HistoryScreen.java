package com.nenton.schools.ui.screens.history;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.HistoryModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;

import dagger.Provides;
import mortar.MortarScope;

/**
 * Created by serge on 22.11.2017.
 */

@Screen(R.layout.screen_history)
public class HistoryScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerHistoryScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(HistoryScreen.class)
        HistoryModel provideHistoryModel() {
            return new HistoryModel();
        }

        @Provides
        @DaggerScope(HistoryScreen.class)
        HistoryPresenter provideHistoryPresenter() {
            return new HistoryPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(HistoryScreen.class)
    public interface Component {
        void inject(HistoryPresenter presenter);

        void inject(HistoryView view);

        RootPresenter getRootPresenter();
    }

    public class HistoryPresenter extends AbstractPresenter<HistoryView, HistoryModel> {

        @Override
        protected void initActivityBarBuilder() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
