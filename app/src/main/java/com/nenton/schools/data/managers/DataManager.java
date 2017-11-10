package com.nenton.schools.data.managers;

import com.google.firebase.auth.FirebaseAuth;
import com.nenton.schools.data.network.RestCallTransformer;
import com.nenton.schools.data.network.RestService;
import com.nenton.schools.data.storage.realm.UserRealm;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.components.DaggerDataManagerComponent;
import com.nenton.schools.di.components.DataManagerComponent;
import com.nenton.schools.di.modules.LocalModule;
import com.nenton.schools.di.modules.NetworkModule;
import com.nenton.schools.utils.App;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by serge on 03.06.2017.
 */

public class DataManager {

    private static final String TAG = "DataManager";
    private static DataManager ourInstance;
    private final RestCallTransformer mRestCallTransformer;

    @Inject
    RestService mRestService;
    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    Retrofit mRetrofit;
    @Inject
    RealmManager mRealmManager;

    public RestService getRestService() {
        return mRestService;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public RealmManager getRealmManager() {
        return mRealmManager;
    }

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    private DataManager() {
        DataManagerComponent component = DaggerService.getComponent(DataManagerComponent.class);
        if (component == null) {
            component = DaggerDataManagerComponent.builder()
                    .appComponent(App.getAppComponent())
                    .localModule(new LocalModule())
                    .networkModule(new NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent.class, component);
        }
        component.inject(this);
        mRestCallTransformer = new RestCallTransformer<>();
    }

    public void saveUserToRealm(UserRealm userRealm) {
        mRealmManager.saveUserInfo(userRealm);
    }

    public Observable<Boolean> checkCompleteRegistrationObs() {
        return getRealmManager().getUserByIdObs(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .flatMap(userRealm -> {
                    boolean isComplete = !userRealm.getName().isEmpty() &&
                            !userRealm.getEmail().isEmpty() &&
                            !userRealm.getState().isEmpty() &&
                            !userRealm.getSchoolDistrict().isEmpty() &&
                            !userRealm.getSchoolPosition().isEmpty();
                    return Observable.just(isComplete);
                });
    }

    public boolean checkCompleteRegistration() {
        UserRealm userRealm = getRealmManager().getUserById(FirebaseAuth.getInstance().getCurrentUser().getUid());
        return !userRealm.getName().isEmpty() &&
                !userRealm.getEmail().isEmpty() &&
                !userRealm.getState().isEmpty() &&
                !userRealm.getSchoolDistrict().isEmpty() &&
                !userRealm.getSchoolPosition().isEmpty();
    }

}
