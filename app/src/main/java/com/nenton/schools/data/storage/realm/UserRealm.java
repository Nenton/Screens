package com.nenton.schools.data.storage.realm;

import com.nenton.schools.data.storage.dto.UserDto;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by serge on 01.06.2017.
 */

public class UserRealm extends RealmObject {

    @PrimaryKey
    private String id;

    private String accountType;
    private boolean educatorVerified;
    private String email;
    private String fbAccountImg;
    private String name;
    private String phone;
    private SchoolRealm school;
    private boolean studentVerified;
    private String teacherRoom;

    public UserRealm() {
    }

    public UserRealm(UserDto user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.accountType = "";
        this.educatorVerified = false;
        this.fbAccountImg = "";
        this.phone = "";
        this.school = null;
        this.accountType = "";
        this.studentVerified = false;
        this.teacherRoom = "";
    }

    public SchoolRealm getSchool() {
        return school;
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

    public boolean isStudentVerified() {
        return studentVerified;
    }

    public String getTeacherRoom() {
        return teacherRoom;
    }

    public void setTelephone(String phone) {

    }

    public void setSchool(String id) {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}