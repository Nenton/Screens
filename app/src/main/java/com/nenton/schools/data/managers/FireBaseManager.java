package com.nenton.schools.data.managers;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.nenton.schools.utils.App;

/**
 * Created by serge on 20.05.2017.
 */

public class FireBaseManager {
    private static FireBaseManager ourInstance;

    private final FirebaseAnalytics mAnalytics;
    private final FirebaseAuth mFirebaseAuth;

    public static FireBaseManager getInstance() {
        if (ourInstance == null){
            ourInstance = new FireBaseManager();
        }
        return ourInstance;
    }


    private FireBaseManager() {
        mAnalytics = FirebaseAnalytics.getInstance(App.getContext());
        mAnalytics.setUserProperty("any_property", "any_value");
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAnalytics getAnalytics() {
        return mAnalytics;
    }

    public FirebaseAuth getAuth() {
        return mFirebaseAuth;
    }
}