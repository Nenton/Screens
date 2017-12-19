package com.nenton.schools.data.storage.realm;

import com.nenton.schools.data.storage.dto.RoomsOfSchool;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 19.12.2017.
 */

public class RoomRealm extends RealmObject {

    @PrimaryKey
    private String id;

    private String school;
    private String name;
    private String teacher;

    public RoomRealm() {
    }

    public RoomRealm(RoomsOfSchool.Room room) {
        this.name = room.getName();
        this.teacher = room.getTeacher();
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }
}
