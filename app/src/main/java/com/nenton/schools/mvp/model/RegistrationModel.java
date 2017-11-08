package com.nenton.schools.mvp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.nenton.schools.data.managers.FireBaseManager;
import com.nenton.schools.data.storage.realm.UserRealm;

import javax.annotation.Nullable;

import rx.Observable;

/**
 * Created by serge on 08.11.2017.
 */

public class RegistrationModel extends AbstractModel {

    private final FireBaseManager mFirebaseManager;
    private final FirebaseAuth mAuth;
    private final FirebaseDatabase mDB;

    public RegistrationModel() {
        mFirebaseManager = FireBaseManager.getInstance();
        mAuth = mFirebaseManager.getFirebaseAuth();
        mDB = mFirebaseManager.getFirebaseDatabase();
    }

    @Nullable
    public Observable<UserRealm> getUser() {
        return mDataManager.getRealmManager().getUserById(mFirebaseManager.getFirebaseAuth().getCurrentUser().getUid());
    }
}
