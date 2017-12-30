package com.nenton.schools.data.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 30.12.2017.
 */

public class StateRealm extends RealmObject {

    @PrimaryKey
    private String mState;

    public StateRealm() {
    }

    public StateRealm(String state) {
        this.mState = state;
    }

    public String getState() {

        return mState;
    }
}
