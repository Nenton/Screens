package com.nenton.schools.ui.screens.schoolPass;

import android.os.Bundle;
import android.os.SystemClock;

import com.nenton.schools.R;
import com.nenton.schools.data.storage.realm.RoomRealm;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.sqopes.DaggerScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.Screen;
import com.nenton.schools.mvp.model.SchoolPassModel;
import com.nenton.schools.mvp.presenters.AbstractPresenter;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.ui.activities.RootActivity;
import com.nenton.schools.ui.custom_views.CustomChronometer;

import java.util.ArrayList;
import java.util.List;

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

        private List<RoomRealm> roomsOfSchool;

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
            roomsOfSchool = mModel.getRoomsOfSchool();
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
                    chronometer.stop();
                    // TODO: 12.12.2017 create Job on create entry in Firebase
                    getView().enableSwitches();
//                    Date date = new Date(SystemClock.elapsedRealtime() - chronometer.getBase());
//                    getRootView().showMessage(String.valueOf(date.getMinutes()) + ":" + String.valueOf(date.getSeconds()));
                } else {
                    getRootView().showMessage("Start time");
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    getView().disableSwitches();
                }
            }
        }


        public void clickOnChoiseClass() {
            if (getView() != null) {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < roomsOfSchool.size(); i++) {
                    RoomRealm room = roomsOfSchool.get(i);
                    list.add(room.getName() + " " + room.getTeacher());
                }
                String[] strings = new String[list.size()];


                for (int i = 0; i < list.size(); i++) {
                    strings[i] = list.get(i);
                }

                if (strings.length == 0) {
                    getRootView().showMessage("List Rooms and Teachers empty");
                } else {
                    getView().showPickerRoomNameAndTeacher(strings);
                }
            }
        }

        public void clickOnRoom(int id) {
            // TODO: 19.12.2017 save SharedPref
            if (getView() != null) {
                RoomRealm room = roomsOfSchool.get(id);
                getView().fillRoom(room.getName(), room.getTeacher());
            }
        }

        public void changeOtherSwitch() {
            if (getRootView() != null && getView() != null) {
                getRootView().showMessage("Other switch not Available");
            }
        }

        public void clickOnHelp() {
            if (getRootView() != null && getView() != null) {
                getRootView().showMessage("Help not available");
            }
        }

    }
}
