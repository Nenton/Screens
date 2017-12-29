package com.nenton.schools.data.storage.realm;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
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
    private String grade;
    private String schoolCity;
    private String schoolDistrict;
    private String schoolId;
    private String schoolName;
    private String schoolPeopleMyUrl;
    private String schoolPhone;
    private String schoolPosition;
    private String schoolStreet;
    private String schoolType;
    private String schoolZip;
    private String state;
    private boolean studentVerified;
    private String teacherRoom;

    public UserRealm() {
    }

    public UserRealm(String email, String uid, String fullName) {
        this.id = uid;
        this.email = email;
        this.name = fullName;
        this.accountType = "";
        this.educatorVerified = false;
        this.fbAccountImg = "";
        this.phone = "";
        this.schoolCity = "";
        this.schoolDistrict = "";
        this.schoolId = "";
        this.schoolName = "";
        this.schoolPhone = "";
        this.schoolPeopleMyUrl = "";
        this.schoolPosition = "";
        this.schoolStreet = "";
        this.schoolType = "";
        this.schoolZip = "";
        this.state = "";
        this.accountType = "";
        this.studentVerified = false;
        this.teacherRoom = "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSchoolCity() {
        return schoolCity;
    }

    public String getSchoolDistrict() {
        return schoolDistrict;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolPeopleMyUrl() {
        return schoolPeopleMyUrl;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public String getSchoolPosition() {
        return schoolPosition;
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

    public String getState() {
        return state;
    }

    public boolean isStudentVerified() {
        return studentVerified;
    }

    public String getTeacherRoom() {
        return teacherRoom;
    }

    public String getGrade() {
        return grade;
    }
}