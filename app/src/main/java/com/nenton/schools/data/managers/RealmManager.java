package com.nenton.schools.data.managers;

import com.nenton.schools.data.storage.dto.RoomsDto;
import com.nenton.schools.data.storage.realm.DistrictRealm;
import com.nenton.schools.data.storage.realm.RoomRealm;
import com.nenton.schools.data.storage.realm.StateRealm;
import com.nenton.schools.data.storage.realm.UserRealm;

import java.util.List;

import io.realm.Realm;
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

    public void saveUserInfo(UserRealm userRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm.insertOrUpdate(userRealm);
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


    public String getSchoolByUser(String uid) {
        UserRealm userRealm = getQueryRealmInstance().where(UserRealm.class)
                .equalTo("id", uid)
                .findFirst();
        return userRealm.getSchoolName();
    }

    public void saveRoomsOfSchool(RoomsDto value, String schoolId) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.insertOrUpdate(new RoomRealm(value, schoolId));
        });
        realm.close();
    }

    public List<RoomRealm> getRoomsOfSchool(String school) {
        return getQueryRealmInstance().where(RoomRealm.class)
                .equalTo("schoolId", school)
                .findAll();
    }

    public void saveState(String stateRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.insertOrUpdate(new StateRealm(stateRealm));
        });
        realm.close();
    }

    public void saveDistrict(String districtRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.insertOrUpdate(new DistrictRealm(districtRealm));
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
}
