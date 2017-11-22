package com.nenton.schools.ui.screens.live;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.LiveModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;

import dagger.Provides;
import mortar.MortarScope;

/**
 * Created by serge on 22.11.2017.
 */
@Screen(R.layout.screen_live)
public class LiveScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerLiveScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(LiveScreen.class)
        LiveModel providesLiveModel() {
            return new LiveModel();
        }

        @Provides
        @DaggerScope(LiveScreen.class)
        LivePresenter providesLivePresenter() {
            return new LivePresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(LiveScreen.class)
    public interface Component {
        void inject(LiveView view);

        void inject(LivePresenter presenter);

        RootPresenter getRootPresenter();
    }

    public class LivePresenter extends AbstractPresenter<LiveView, LiveModel> {

        @Override
        protected void initActivityBarBuilder() {

        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }
    }
}
