package com.nenton.schools.data.storage.realm;

import com.nenton.schools.data.storage.dto.RoomDto;
import com.nenton.schools.data.storage.dto.SchoolDto;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 03.01.2018.
 */

public class SchoolRealm extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;
    private String position;
    private String schoolCity;
    private String schoolPhone;
    private String schoolStreet;
    private String schoolType;
    private String schoolZip;

    private RealmList<RoomRealm> roomRealms;

    private StateRealm schoolState;
    private DistrictRealm schoolDistrict;

    public SchoolRealm() {
    }

    public SchoolRealm(SchoolDto value, StateRealm state, DistrictRealm district) {
        this.id = value.getId();
        this.name = value.getName();
        this.position = value.getPosition();
        this.schoolCity = value.getSchoolCity();
        this.schoolPhone = value.getSchoolPhone();
        this.schoolStreet = value.getSchoolStreet();
        this.schoolType = value.getSchoolType();
        this.schoolZip = value.getSchoolZip();
        this.schoolState = state;
        this.schoolDistrict = district;

        roomRealms = new RealmList<>();

        for (RoomDto room : value.getRooms().values()) {
            roomRealms.add(new RoomRealm(room));
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public String getSchoolStreet() {
        return schoolStreet;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public String getSchoolZip() {
        return schoolZip;
    }

    public RealmList<RoomRealm> getRoomRealms() {
        return roomRealms;
    }

    public StateRealm getSchoolState() {
        return schoolState;
    }

    public DistrictRealm getSchoolDistrict() {
        return schoolDistrict;
    }
}
