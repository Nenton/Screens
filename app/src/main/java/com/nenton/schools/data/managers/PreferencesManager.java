package com.nenton.schools.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nenton.schools.data.storage.dto.UserDto;
import com.nenton.schools.data.storage.realm.UserRealm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferencesManager {

    private static final String USER_FULL_NAME = "USER_FULL_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_GRADE = "USER_GRADE";
    private static final String USER_STATE = "USER_STATE";
    private static final String USER_DISTRICT = "USER_DISTRICT";
    private static final String USER_LEVEL_EDUCATION = "USER_LEVEL_EDUCATION";
    private static final String USER_DETAILS = "USER_DETAILS";
    private static final String USER_SCHOOL = "USER_SCHOOL";

    private final SharedPreferences mSharedPreferences;

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public PreferencesManager(Context context) {
        mSharedPreferences = context.getSharedPreferences("Schools", Context.MODE_PRIVATE);
    }

    public String getSchool() {
        return mSharedPreferences.getString(USER_SCHOOL, "");
    }

//    public void saveCurrentUser(UserDto userDto) {
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//
//        editor.putString(USER_FULL_NAME, userDto.getFullName());
//        editor.putString(USER_ID, userDto.getId());
//        editor.putString(USER_EMAIL, userDto.getEmail());
//        editor.putString(USER_GRADE, userDto.getGrade());
//        editor.putString(USER_STATE, userDto.getState());
//        editor.putString(USER_DISTRICT, userDto.getDistrict());
//        editor.putString(USER_LEVEL_EDUCATION, userDto.getLevelEducation());
//        editor.putString(USER_DETAILS, userDto.getDetails());
////        editor.putString(USER_SCHOOL, userDto.get)
//        editor.apply();
//    }
//
//    public UserDto getCurrentUser(){
//        UserDto user = new UserDto();
//        user.setFullName(mSharedPreferences.getString(USER_FULL_NAME, ""));
//        user.setId(mSharedPreferences.getString(USER_ID, ""));
//        user.setEmail(mSharedPreferences.getString(USER_EMAIL, ""));
//        user.setGrade(mSharedPreferences.getString(USER_GRADE, ""));
//        user.setState(mSharedPreferences.getString(USER_STATE, ""));
//        user.setDistrict(mSharedPreferences.getString(USER_DISTRICT, ""));
//        user.setLevelEducation(mSharedPreferences.getString(USER_LEVEL_EDUCATION, ""));
//        user.setDetails(mSharedPreferences.getString(USER_DETAILS, ""));
//        return user;
//    }

//    public String getLastProductUpdate() {
//        return mSharedPreferences.getString(PRODUCT_LAST_UPDATE_KEY, DEFAULT_LAST_UPDATE);
//    }
//
//    public void saveLastProductUpdate(String lastModified) {
//        SharedPreferences.Editor editor = mSharedPreferences.edit();
//        editor.putString(PRODUCT_LAST_UPDATE_KEY, lastModified);
//        editor.apply();
//    }

}