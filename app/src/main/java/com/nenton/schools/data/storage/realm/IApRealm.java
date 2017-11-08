package com.nenton.schools.data.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 09.11.2017.
 */

public class IApRealm extends RealmObject {

    @PrimaryKey
    private String id;
}
