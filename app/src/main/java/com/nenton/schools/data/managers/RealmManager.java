package com.nenton.schools.data.managers;

import com.nenton.schools.data.storage.realm.UserRealm;

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

    public Observable<UserRealm> getUserById(String id){
        RealmResults<UserRealm> user = getQueryRealmInstance().where(UserRealm.class)
                .equalTo("id", id)
                .findAllAsync();
        return user.asObservable()
                .filter(RealmResults::isLoaded)
                .flatMap(Observable::from)
                .first();
    }
}
