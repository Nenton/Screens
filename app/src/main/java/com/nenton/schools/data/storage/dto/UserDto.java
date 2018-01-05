package com.nenton.schools.data.storage.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by serge on 08.11.2017.
 */

public class UserDto {

    private String id;

    private String accountType;
    private boolean educatorVerified;
    private String email;
    private String fbAccountImg;
    private String name;
    private String phone;
    private String schoolId;
    private boolean studentVerified;
    private String teacherRoom;

    public UserDto() {
    }

    public UserDto(String email, String uid, String fullName) {
        this.email = email;
        this.id = uid;
        this.name = fullName;
    }

    public String getId() {
        return id;
    }

    public String getAccountType() {
        return accountType;
    }

    public boolean isEducatorVerified() {
        return educatorVerified;
    }

    public String getEmail() {
        return email;
    }

    public String getFbAccountImg() {
        return fbAccountImg;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public boolean isStudentVerified() {
        return studentVerified;
    }

    public String getTeacherRoom() {
        return teacherRoom;
    }
}
