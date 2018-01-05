package com.nenton.schools.data.storage.dto;

import java.util.HashMap;
import java.util.List;

/**
 * Created by serge on 05.01.2018.
 */

public class SchoolDto {

    private String id;
    private String districtId;
    private String name;
    private String position;
    private String schoolCity;
    private String schoolPhone;
    private String schoolStreet;
    private String schoolType;
    private String schoolZip;
    private String stateId;

    private HashMap<String, RoomDto> rooms;


    public SchoolDto() {

    }

    public String getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getName() {
        return name;
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

    public String getStateId() {
        return stateId;
    }

    public HashMap<String, RoomDto> getRooms() {
        return rooms;
    }

}
