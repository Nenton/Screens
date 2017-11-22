package com.nenton.schools.ui.screens.shop;

import android.os.Bundle;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.ShopModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.IShopPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;
import com.nenton.schools.ui.screens.schoolPass.SchoolPassScreen;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;

/**
 * Created by serge on 08.11.2017.
 */
@Screen(R.layout.screen_shop)
public class ShopScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerShopScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(ShopScreen.class)
        ShopModel provideMainModel() {
            return new ShopModel();
        }

        @Provides
        @DaggerScope(ShopScreen.class)
        ShopPresenter provideMainPresenter() {
            return new ShopPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(ShopScreen.class)
    public interface Component {
        void inject(ShopPresenter presenter);

        void inject(ShopView view);

        RootPresenter getRootPresenter();
    }

    public class ShopPresenter extends AbstractPresenter<ShopView, ShopModel> implements IShopPresenter {

        @Override
        protected void initActivityBarBuilder() {
            mRootPresenter.newRootActivityBarBuilder()
                    .setShowBottomNav(false)
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((ShopScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
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
