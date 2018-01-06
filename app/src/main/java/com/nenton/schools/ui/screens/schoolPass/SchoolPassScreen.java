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
import com.nenton.schools.ui.screens.shop.ShopScreen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dagger.Provides;
import flow.Flow;
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
        private RoomRealm mRoom;
        private long baseChronometer = 0;
        private long timeFromChronometer = 0;
        private String mCurrentRoom = "";
        private String mCurrentTeacher = "";

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
                if (!mCurrentRoom.isEmpty() || !mCurrentTeacher.isEmpty()) {
                    CustomChronometer chronometer = getView().getChronometer();
                    if (chronometer.isRunning()) {
                        getView().showDialogStop();
                    } else {
                        if (timeFromChronometer == 0) {
                            baseChronometer = new Date().getTime();
                            timeFromChronometer = SystemClock.elapsedRealtime();
                            chronometer.setBase(timeFromChronometer);
                        } else {
                            chronometer.setBase(SystemClock.elapsedRealtime() - timeFromChronometer);
                        }
                        getView().disableSwitches();
                        getRootView().showMessage("Start time");
                        chronometer.start();
                        ((RootActivity) getRootView()).stateBottomNavView(false);
                    }
                } else {
                    getRootView().showMessage("For start the timer will choice room or classroom");
                }
            }
        }

        public void clickOnChoiseClass() {
            if (getRootView() != null && getView() != null) {
                if (timeFromChronometer == 0) {
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
                } else {
                    getRootView().showMessage("For choice of the room will stop the timer");
                }
            }
        }

        public void clickOnRoom(int id) {
            if (getView() != null) {
                if (mRoom != null && mRoom.equals(roomsOfSchool.get(id))) {
                    getRootView().showMessage("You are already here");
                    return;
                }
                mRoom = roomsOfSchool.get(id);
                mCurrentRoom = mRoom.getName();
                mCurrentTeacher = mRoom.getTeacher();
                getView().fillRoom(mCurrentRoom, mCurrentTeacher);
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

        public void clickOnShop() {
            if (getView() != null) {
                if (timeFromChronometer == 0) {
                    Flow.get(getView()).set(new ShopScreen());
                } else {
                    getRootView().showMessage("For go in shop will stop the timer");
                }
            }
        }

        public void stopTimer() {
            if (getView() != null) {
                saveEntityToHistory();
                getView().getChronometer().stop();
                getView().enableSwitches();
                timeFromChronometer = 0;
                baseChronometer = 0;
                ((RootActivity) getRootView()).stateBottomNavView(true);
            }
        }

        private void saveEntityToHistory() {
            mModel.saveEntityToHistory(mCurrentRoom,
                    mCurrentTeacher,
                    baseChronometer,
                    new Date().getTime());
        }

        public void pauseTimer() {
            if (getView() != null) {
                CustomChronometer chronometer = getView().getChronometer();
                timeFromChronometer = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();
            }
        }

        public void choiceRoom(String tag) {
            mCurrentRoom = tag;
            mCurrentTeacher = "";
        }

        public void changeSwitch() {
            mRoom = null;
        }

        public void onSwitchers(boolean isChangeOne) {
            if (!isChangeOne) {
                mCurrentRoom = "";
                mCurrentTeacher = "";
            }
        }
    }
}
