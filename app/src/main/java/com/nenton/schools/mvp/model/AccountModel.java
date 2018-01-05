package com.nenton.schools.mvp.model;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.nenton.schools.data.managers.FireBaseManager;
import com.nenton.schools.data.storage.realm.DistrictRealm;
import com.nenton.schools.data.storage.realm.SchoolRealm;
import com.nenton.schools.data.storage.realm.StateRealm;
import com.nenton.schools.data.storage.realm.UserRealm;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by serge on 08.11.2017.
 */

public class AccountModel extends AbstractModel {

    private final FireBaseManager mFirebaseManager;
    private final FirebaseAuth mAuth;
    private final FirebaseDatabase mDB;

    public AccountModel() {
        mFirebaseManager = FireBaseManager.getInstance();
        mAuth = mFirebaseManager.getFirebaseAuth();
        mDB = mFirebaseManager.getFirebaseDatabase();
    }

    @Nullable
    public Observable<UserRealm> getUser() {
        return mDataManager.getRealmManager().getUserByIdObs(mFirebaseManager.getFirebaseAuth().getCurrentUser().getUid());
    }

    public void saveUserInfo(Context context, Map<String, Object> mapFirebase) {
        mDB.getReference().child("users").child(mAuth.getCurrentUser().getUid()).updateChildren(mapFirebase).addOnCompleteListener(task -> {
            mDataManager.getRealmManager().saveUserInfo(context, mapFirebase);
        });
    }

    public void logoutUser() {
        mDataManager.deleteUserFromRealm(mAuth.getCurrentUser().getUid());
        mAuth.signOut();
    }

    public Observable<Boolean> checkCompleteRegistration() {
        return mDataManager.checkCompleteRegistrationObs();
    }

    public String[] getDistricts() {
        List<DistrictRealm> districts = mDataManager.getRealmManager().getDistricts();
        String[] strings = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            strings[i] = districts.get(i).getDistrict();
        }
        return strings;
    }

    public String[] getStates() {
        List<StateRealm> districts = mDataManager.getRealmManager().getStates();
        String[] strings = new String[districts.size()];
        for (int i = 0; i < districts.size(); i++) {
            strings[i] = districts.get(i).getState();
        }
        return strings;
    }

    public String[] getGrades() {
        String[] strings = new String[]{"First", "Second", "Three"};
        // TODO: 19.12.2017 get grade
        return strings;
    }

    public String[] getSchools() {
        List<SchoolRealm> mSchools = mDataManager.getRealmManager().getSchools();
        String[] strings = new String[mSchools.size()];
        for (int i = 0; i < mSchools.size(); i++) {
            strings[i] = mSchools.get(i).getName();
        }
        return strings;
    }

//    @Nullable
//    public SchoolRealm getSchool() {
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null){
//            return mDataManager.getRealmManager().getSchoolByUser(currentUser.getUid());
//        } else {
//            return null;
//        }
//    }
}
