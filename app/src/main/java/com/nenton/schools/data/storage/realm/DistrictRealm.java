package com.nenton.schools.data.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 30.12.2017.
 */

public class DistrictRealm extends RealmObject{

    @PrimaryKey
    private String id;
    private String mDistrict;


    public DistrictRealm() {
    }

    public String getId() {
        return id;
    }

    public DistrictRealm(String id, String district) {
        this.id = id;
        this.mDistrict = district;
    }

    public String getDistrict() {
        return mDistrict;
    }
}
