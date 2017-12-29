package com.nenton.schools.ui.screens.account;

import android.os.Bundle;

import com.nenton.schools.R;
import com.nenton.schools.data.storage.realm.UserRealm;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.AccountModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;
import com.nenton.schools.ui.screens.auth.AuthScreen;
import com.nenton.schools.ui.screens.shop.ShopScreen;

import java.util.HashMap;
import java.util.Map;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import rx.Subscriber;

/**
 * Created by serge on 08.11.2017.
 */

@Screen(R.layout.screen_account)
public class AccountScreen extends AbstractScreen<RootActivity.RootComponent> {
    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerAccountScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(AccountScreen.class)
        AccountModel provideRegistrationModel() {
            return new AccountModel();
        }

        @Provides
        @DaggerScope(AccountScreen.class)
        AccountPresenter provideMainPresenter() {
            return new AccountPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(AccountScreen.class)
    public interface Component {
        void inject(AccountPresenter presenter);

        void inject(AccountView view);

        RootPresenter getRootPresenter();
    }

    public class AccountPresenter extends AbstractPresenter<AccountView, AccountModel> {

        @Override
        protected void initActivityBarBuilder() {
            mCompSubs.add(
                    mModel.checkCompleteRegistration().subscribe(aBoolean -> {
                        if (aBoolean) {
                            mRootPresenter.newRootActivityBarBuilder()
                                    .setShowBottomNav(true)
                                    .build();
                        } else {
                            mRootPresenter.newRootActivityBarBuilder()
                                    .setShowBottomNav(false)
                                    .build();
                        }
                    }));
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((AccountScreen.Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            mCompSubs.add(mModel.getUser().subscribe(new RealmSubscriber()));
        }

        public void clickComplete() {
            if (verifyUserInfo() && getView() != null) {
                // TODO: 29.12.2017 Сохранить в Realm
                Map<String, Object> mapFirebase = new HashMap<>();
                mapFirebase.put("name", getView().getName());
                mapFirebase.put("email", getView().getEmail());
                mapFirebase.put("grade", getView().getGrade());
                mapFirebase.put("state", getView().getState());
                mapFirebase.put("schoolDistrict", getView().getDistrict());
                mapFirebase.put("schoolType", getView().getTypeEducation());
                mapFirebase.put("schoolName", getView().getSchools());

                mModel.saveUserInfo(mapFirebase);

                Flow.get(getView()).set(new ShopScreen());
            } else {
                getView().showNotCompleted();
//                getRootView().showMessage("Not complete registration");
            }
        }

        private boolean verifyUserInfo() {
            return !getView().getName().isEmpty() &&
                    !getView().getEmail().isEmpty() &&
                    !getView().getGrade().isEmpty() &&
                    !getView().getState().isEmpty() &&
                    !getView().getDistrict().isEmpty() &&
                    !getView().getTypeEducation().isEmpty() &&
                    !getView().getSchools().isEmpty();
        }

        public void clickState() {
            if (getView() != null) {
                getView().showPickerState();
            }
        }

        public void clickDistrict() {
            if (getView() != null) {
                String[] strings = new String[]{"First", "Second", "Three"};
                // TODO: 19.12.2017 get districts
                getView().showPickerDistrict(strings);
            }
        }

        public void clickOnLogout() {
            mModel.logoutUser();
            Flow.get(getView()).set(new AuthScreen());
        }

        public void clickOnGrade() {
            if (getView() != null) {
                String[] strings = new String[]{"First", "Second", "Three"};
                // TODO: 19.12.2017 get grades
                getView().showPickerGrade(strings);
            }
        }

        public void clickOnSchool() {
            if (getView() != null){
                String[] strings = new String[]{"First", "Second", "Three"};
                // TODO: 19.12.2017 get school
                getView().showPickerSchool(strings);
            }
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
