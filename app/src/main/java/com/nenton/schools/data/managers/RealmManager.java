package com.nenton.schools.data.managers;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nenton.schools.R;
import com.nenton.schools.data.storage.dto.EntityHistoryPass;
import com.nenton.schools.data.storage.dto.RoomDto;
import com.nenton.schools.data.storage.dto.SchoolDto;
import com.nenton.schools.data.storage.realm.DistrictRealm;
import com.nenton.schools.data.storage.realm.EntityHistoryPassRealm;
import com.nenton.schools.data.storage.realm.RoomRealm;
import com.nenton.schools.data.storage.realm.SchoolRealm;
import com.nenton.schools.data.storage.realm.StateRealm;
import com.nenton.schools.data.storage.realm.UserRealm;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by serge on 09.01.2017.
 */

public class RealmManager {

    private Realm mRealmInstance;

    public Realm getQueryRealmInstance() {
        if (mRealmInstance == null || mRealmInstance.isClosed()) {
            mRealmInstance = Realm.getDefaultInstance();
        }
        return mRealmInstance;
    }

    public void deleteFromRealm(Class<? extends RealmObject> entityRealmClass, String id) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject entity = realm.where(entityRealmClass).equalTo("id", id).findFirst();
        if (entity != null) {
            realm.executeTransaction(realm1 -> entity.deleteFromRealm());
        }
        realm.close();
    }

    public void saveUserInfo(UserRealm user, String schoolId) {
        SchoolRealm school = getQueryRealmInstance().where(SchoolRealm.class)
                .equalTo("id", schoolId)
                .findFirst();
        user.setSchool(school);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm.insertOrUpdate(user);
        });
        realm.close();
    }


    public void saveUserInfo(Context context, Map<String, Object> mapFirebase) {
        SchoolRealm school = getQueryRealmInstance().where(SchoolRealm.class)
                .equalTo("id", mapFirebase.get(context.getResources().getString(R.string.user_school_id)).toString())
                .findFirst();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            UserRealm userRealm = realm.where(UserRealm.class).equalTo("id", FirebaseAuth.getInstance().getCurrentUser().getUid()).findFirst();
            userRealm.setName(mapFirebase.get(context.getResources().getString(R.string.user_name)).toString());
            userRealm.setEmail(mapFirebase.get(context.getResources().getString(R.string.user_email)).toString());
            userRealm.setTelephone(mapFirebase.get(context.getResources().getString(R.string.user_telephone)).toString());
            userRealm.setSchool(school);
        });
        realm.close();
    }

    public Observable<UserRealm> getUserByIdObs(String id) {
        RealmResults<UserRealm> user = getQueryRealmInstance().where(UserRealm.class)
                .equalTo("id", id)
                .findAllAsync();
        return user.asObservable()
                .filter(RealmResults::isLoaded)
                .flatMap(Observable::from)
                .first();
    }

    public UserRealm getUserById(String id) {
        return getQueryRealmInstance().where(UserRealm.class)
                .equalTo("id", id)
                .findFirst();
    }


    public SchoolRealm getSchoolByUser(String uid) {
        UserRealm userRealm = getQueryRealmInstance().where(UserRealm.class)
                .equalTo("id", uid)
                .findFirst();

        SchoolRealm schoolRealm = getQueryRealmInstance().where(SchoolRealm.class)
                .equalTo("id", userRealm.getSchool().getId())
                .findFirst();

        return schoolRealm;
    }

    public void saveRoomsOfSchool(RoomDto value) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.insertOrUpdate(new RoomRealm(value));
        });
        realm.close();
    }

    public void saveState(String id, String stateRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.insertOrUpdate(new StateRealm(id, stateRealm));
        });
        realm.close();
    }

    public void saveDistrict(String id, String districtRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.insertOrUpdate(new DistrictRealm(id, districtRealm));
        });
        realm.close();
    }

    public List<DistrictRealm> getDistricts() {
        return getQueryRealmInstance().where(DistrictRealm.class)
                .findAll();
    }

    public List<StateRealm> getStates() {
        return getQueryRealmInstance().where(StateRealm.class)
                .findAll();
    }

    public List<SchoolRealm> getSchools() {
        return getQueryRealmInstance().where(SchoolRealm.class)
                .findAll();
    }

    public void saveSchool(SchoolDto value) {
        StateRealm state = getQueryRealmInstance().where(StateRealm.class)
                .equalTo("id", value.getStateId())
                .findFirst();
        DistrictRealm district = getQueryRealmInstance().where(DistrictRealm.class)
                .equalTo("id", value.getDistrictId())
                .findFirst();

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.insertOrUpdate(new SchoolRealm(value, state, district));
        });
        realm.close();
    }

    public void deleteUserFromRealm() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<UserRealm> entity = realm.where(UserRealm.class).findAll();
        if (entity != null) {
            realm.executeTransaction(realm1 -> entity.deleteAllFromRealm());
        }
        realm.close();
    }

    public void saveHistory(EntityHistoryPass entityHistoryPass, String key) {
        FirebaseUser currentUser = FireBaseManager.getInstance().getFirebaseAuth().getCurrentUser();
        if (currentUser != null) {
            UserRealm user = getQueryRealmInstance()
                    .where(UserRealm.class)
                    .equalTo("id", currentUser.getUid())
                    .findFirst();

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(realm1 -> {
                EntityHistoryPassRealm entityHistoryPassRealm = new EntityHistoryPassRealm(entityHistoryPass, key);
                realm1.insertOrUpdate(entityHistoryPassRealm);
                user.getHistories().add(entityHistoryPassRealm);
            });
            realm.close();
        }
    }

    public Observable<RealmList<EntityHistoryPassRealm>> getHistories() {
        FirebaseUser currentUser = FireBaseManager.getInstance().getFirebaseAuth().getCurrentUser();
        if (currentUser != null) {
            return getQueryRealmInstance().where(UserRealm.class)
                    .equalTo("id", currentUser.getUid())
                    .findFirst().getHistories().asObservable();

        } else {
            return Observable.empty();
        }
    }
}
