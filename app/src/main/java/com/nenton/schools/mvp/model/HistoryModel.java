package com.nenton.schools.mvp.model;

import com.nenton.schools.data.storage.realm.EntityHistoryPassRealm;

import io.realm.RealmList;
import rx.Observable;
import rx.Subscription;

/**
 * Created by serge on 22.11.2017.
 */

public class HistoryModel extends AbstractModel{
    public Observable<RealmList<EntityHistoryPassRealm>> getHistories() {
        return mDataManager.getRealmManager().getHistories();
    }
}
