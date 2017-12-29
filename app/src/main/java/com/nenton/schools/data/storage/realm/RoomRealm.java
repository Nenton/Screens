package com.nenton.schools.data.storage.realm;

import com.nenton.schools.data.storage.dto.RoomsDto;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 19.12.2017.
 */

public class RoomRealm extends RealmObject {

    @PrimaryKey
    private String id;

    private String schoolId;
    private String name;
    private String teacher;

    public RoomRealm() {
    }

    public RoomRealm(RoomsDto room, String schoolId) {
        this.id = room.getName();
        this.name = room.getName();
        this.teacher = room.getTeacher();
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }
}
