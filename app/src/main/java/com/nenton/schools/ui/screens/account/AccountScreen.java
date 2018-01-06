package com.nenton.schools.ui.screens.account;

import android.os.Bundle;

import com.nenton.schools.R;
import com.nenton.schools.data.storage.realm.SchoolRealm;
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

        private String mIdSchool = "";

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
            mRootPresenter.updateInfoAboutUser();
//            mRootPresenter.updateInfoAboutStatesDistrictsSchools();
            mCompSubs.add(mModel.getUser().subscribe(new RealmSubscriber()));
        }

        public void clickComplete() {
            if (getRootView() != null && getView() != null && verifyUserInfo()) {
                RootActivity rootView = (RootActivity) getRootView();
                Map<String, Object> mapFirebase = new HashMap<>();
                mapFirebase.put(rootView.getResources().getString(R.string.user_name), getView().getName());
                mapFirebase.put(rootView.getResources().getString(R.string.user_email), getView().getEmail());
                mapFirebase.put(rootView.getResources().getString(R.string.user_telephone), getView().getTelephone());
                mapFirebase.put(rootView.getResources().getString(R.string.user_school_id), mIdSchool);

                mModel.saveUserInfo(getView().getContext(), mapFirebase);
                getRootView().showMessage("Changes saved!");
//                Flow.get(getView()).set(new SchoolPassScreen());
            } else {
                getView().showNotCompleted();
//                getRootView().showMessage("Not complete registration");
            }
        }

        private boolean verifyUserInfo() {
            return !getView().getName().isEmpty() &&
                    !getView().getEmail().isEmpty() &&
                    !getView().getGrade().isEmpty() &&
                    !getView().getSchools().isEmpty();
        }

        public void clickState() {
            if (getView() != null) {
                getView().showPickerState(mModel.getStates());
            }
        }

        public void clickDistrict() {
            if (getView() != null) {
                getView().showPickerDistrict(mModel.getDistricts());
            }
        }

        public void clickOnLogout() {
            mModel.logoutUser();
            Flow.get(getView()).set(new AuthScreen());
            mModel.deleteUserFromRealm();
        }

        public void clickOnGrade() {
            if (getView() != null) {
                getView().showPickerGrade(mModel.getGrades());
            }
        }

        public void clickOnSchool() {
            if (getView() != null) {
                getView().showPickerSchool(mModel.getSchools());
            }
        }

        public void choiceSchool(int position) {
            if (getView() != null && position >= 0) {
                SchoolRealm schoolRealm = mModel.getSchools().get(position);
                mIdSchool = schoolRealm.getId();
                getView().changeSchool(schoolRealm);
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
                    if (user.getSchool() != null) {
                        mIdSchool = user.getSchool().getId();
                    }
                }
            }
        }
    }
}
