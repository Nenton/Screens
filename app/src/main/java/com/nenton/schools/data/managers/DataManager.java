package com.nenton.schools.data.managers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nenton.schools.data.network.RestCallTransformer;
import com.nenton.schools.data.network.RestService;
import com.nenton.schools.data.storage.dto.RoomsDto;
import com.nenton.schools.data.storage.realm.RoomRealm;
import com.nenton.schools.data.storage.realm.StateRealm;
import com.nenton.schools.data.storage.realm.UserRealm;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.components.DaggerDataManagerComponent;
import com.nenton.schools.di.components.DataManagerComponent;
import com.nenton.schools.di.modules.LocalModule;
import com.nenton.schools.di.modules.NetworkModule;
import com.nenton.schools.utils.App;

import java.util.List;

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
//                            !userRealm.getSchoolDistrict().isEmpty() &&
                            !userRealm.getSchoolName().isEmpty() &&
                            !userRealm.getSchoolPosition().isEmpty();
                    return Observable.just(isComplete);
                });
    }

    public boolean checkCompleteRegistration() {
        UserRealm userRealm = getRealmManager().getUserById(FirebaseAuth.getInstance().getCurrentUser().getUid());

        return !userRealm.getName().isEmpty() &&
                !userRealm.getEmail().isEmpty() &&
                !userRealm.getGrade().isEmpty() &&
                !userRealm.getState().isEmpty() &&
                !userRealm.getSchoolDistrict().isEmpty() &&
                !userRealm.getSchoolType().isEmpty() &&
                !userRealm.getSchoolName().isEmpty();
    }

    public void saveRoomsOfSchool(){
        String uid = FireBaseManager.getInstance().getFirebaseAuth().getCurrentUser().getUid();
//        if (!mPreferencesManager.getSchoolByUser().isEmpty()) {
            FirebaseDatabase firebaseDatabase = FireBaseManager.getInstance().getFirebaseDatabase();
            firebaseDatabase.getReference().child("schools").child(mRealmManager.getSchoolByUser(uid)).child("rooms").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        saveRoomsOfSchoolToRealm(postSnapshot.getValue(RoomsDto.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//        }
    }

    public List<RoomRealm> getRoomsOfSchool() {
        return mRealmManager.getRoomsOfSchool(mRealmManager.getSchoolByUser(FireBaseManager.getInstance().getFirebaseAuth().getCurrentUser().getUid()));
    }

    private void saveRoomsOfSchoolToRealm(RoomsDto value) {
        mRealmManager.saveRoomsOfSchool(value, mRealmManager.getSchoolByUser(FireBaseManager.getInstance().getFirebaseAuth().getCurrentUser().getUid()));
    }

    public void deleteUserFromRealm(String uid) {
        mRealmManager.deleteFromRealm(UserRealm.class, uid);
    }

    public void updateUserInfo() {
        FireBaseManager instance = FireBaseManager.getInstance();
        DatabaseReference user = instance.getFirebaseDatabase().getReference().child("users").child(instance.getFirebaseAuth().getCurrentUser().getUid());
        if (user != null) {
            user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserRealm userRealm = dataSnapshot.getValue(UserRealm.class);
                    saveUserToRealm(userRealm);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void uploadStatesDistricts() {
        FireBaseManager instance = FireBaseManager.getInstance();
        DatabaseReference states = instance.getFirebaseDatabase().getReference().child("states");
        if (states != null){
            states.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String stateRealm = data.getValue(String.class);
                        mRealmManager.saveState(stateRealm);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        DatabaseReference districts = instance.getFirebaseDatabase().getReference().child("districts");
        if (districts != null){
            districts.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String districtRealm = data.getValue(String.class);
                        mRealmManager.saveDistrict(districtRealm);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
}
