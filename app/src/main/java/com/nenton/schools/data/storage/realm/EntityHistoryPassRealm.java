package com.nenton.schools.data.storage.realm;

import com.nenton.schools.data.storage.dto.EntityHistoryPass;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 06.01.2018.
 */

public class EntityHistoryPassRealm extends RealmObject {

    @PrimaryKey
    private String id;
    private String schoolId;
    private String room;
    private String teacher;
    private long dateStart;
    private long dateEnd;

    public EntityHistoryPassRealm() {
    }

    public EntityHistoryPassRealm(EntityHistoryPass entityHistoryPass, String key) {
        id = key;
        schoolId = entityHistoryPass.getSchoolId();
        room = entityHistoryPass.getRoom();
        teacher = entityHistoryPass.getTeacher();
        dateStart = entityHistoryPass.getStart();
        dateEnd = entityHistoryPass.getEnd();
    }

    public String getId() {
        return id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getRoom() {
        return room;
    }

    public String getTeacher() {
        return teacher;
    }

    public long getDateStart() {
        return dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }
}
