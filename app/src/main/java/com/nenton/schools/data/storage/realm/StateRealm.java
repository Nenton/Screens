package com.nenton.schools.data.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 30.12.2017.
 */

public class StateRealm extends RealmObject {

    @PrimaryKey
    private String id;

    public String getId() {
        return id;
    }

    private String mState;

    public StateRealm() {
    }

    public StateRealm(String id, String state) {
        this.id = id;
        this.mState = state;
    }

    public String getState() {

        return mState;
    }
}
