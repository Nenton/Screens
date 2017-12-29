package com.nenton.schools.mvp.model;

import com.nenton.schools.data.storage.realm.RoomRealm;

import java.util.List;

/**
 * Created by serge on 15.11.2017.
 */

public class SchoolPassModel extends AbstractModel{

    public List<RoomRealm> getRoomsOfSchool(){
        mDataManager.saveRoomsOfSchool();
        return mDataManager.getRoomsOfSchool();
    }
}
