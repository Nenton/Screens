package com.nenton.schools.data.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 08.11.2017.
 */

public class ClassroomsRealm extends RealmObject {

    @PrimaryKey
    private String id;
    private String classroom;

    public ClassroomsRealm() {
    }

    public String getClassroom() {
        return classroom;
    }
}
