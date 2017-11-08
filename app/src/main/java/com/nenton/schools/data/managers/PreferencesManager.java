package com.nenton.schools.data.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferencesManager {

    private static final String PROFILE_USER_ID = "PROFILE_USER_ID";
    private static final String PROFILE_NAME = "PROFILE_NAME";
    private static final String PROFILE_LOGIN = "PROFILE_LOGIN";
    private static final String PROFILE_AVATAR_KEY = "PROFILE_AVATAR_KEY";
    private static final String PROFILE_AUTH_TOKEN_KEY = "PROFILE_AUTH_TOKEN_KEY";
    private static final String PRODUCT_LAST_UPDATE_KEY = "PRODUCT_LAST_UPDATE_KEY";
    private static final String SEARCH_SUGGESTION_QUERY_KEY = "SEARCH_SUGGESTION_QUERY_KEY";
    private static final String DEFAULT_LAST_UPDATE = "Thu Jan 1 1970 00:00:00 GMT+0000 (UTC)";

    private final SharedPreferences mSharedPreferences;

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public PreferencesManager(Context context) {
        mSharedPreferences = context.getSharedPreferences("Photon", Context.MODE_PRIVATE);
    }

    public String getLastProductUpdate() {
        return mSharedPreferences.getString(PRODUCT_LAST_UPDATE_KEY, DEFAULT_LAST_UPDATE);
    }

    public void saveLastProductUpdate(String lastModified) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PRODUCT_LAST_UPDATE_KEY, lastModified);
        editor.apply();
    }

    public void saveUserAvatar(@NonNull String uri) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PROFILE_AVATAR_KEY, uri);
        editor.apply();
    }


    public void saveToken(String token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PROFILE_AUTH_TOKEN_KEY, token);
        editor.apply();
    }

    public boolean isUserAuth() {
        return mSharedPreferences.getString(PROFILE_AUTH_TOKEN_KEY, null) != null;
    }


    @Nullable
    public String getUserId() {
        return mSharedPreferences.getString(PROFILE_USER_ID, null);
    }

    @NonNull
    public String getUserName() {
        return mSharedPreferences.getString(PROFILE_NAME, "Не авторизован");
    }

    @NonNull
    public String getUserLogin() {
        return mSharedPreferences.getString(PROFILE_LOGIN, "");
    }

    @Nullable
    public String getUserAvatar() {
        return mSharedPreferences.getString(PROFILE_AVATAR_KEY, null);
    }

    public String getAuthToken() {
        return mSharedPreferences.getString(PROFILE_AUTH_TOKEN_KEY, null);
    }

    public void saveSearchString(String s) {
        Set<String> stringSet = mSharedPreferences.getStringSet(SEARCH_SUGGESTION_QUERY_KEY, new HashSet<String>());
        stringSet.add(s);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(SEARCH_SUGGESTION_QUERY_KEY, stringSet);
        editor.apply();
    }

    public void removeSearchString(String s) {
        Set<String> stringSet = mSharedPreferences.getStringSet(SEARCH_SUGGESTION_QUERY_KEY, new HashSet<String>());
        stringSet.remove(s);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(SEARCH_SUGGESTION_QUERY_KEY, stringSet);
        editor.apply();
    }

    public List<String> getSearchStrings() {
        List<String> strings = new ArrayList<>();
        Set<String> stringSet = mSharedPreferences.getStringSet(SEARCH_SUGGESTION_QUERY_KEY, new HashSet<String>());
        for (String s : stringSet) {
            strings.add(s);
        }
        return strings;
    }
}