package com.nenton.schools.data.storage.realm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 01.06.2017.
 */

public class UserRealm extends RealmObject implements Serializable {

    @PrimaryKey
    private String id;
    private String name;
    private String login;
    private String avatar;

    public UserRealm() {
    }
}