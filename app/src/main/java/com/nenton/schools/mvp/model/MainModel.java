package com.nenton.schools.mvp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nenton.schools.data.managers.FireBaseManager;

import rx.Observable;

/**
 * Created by serge on 07.11.2017.
 */

public class MainModel extends AbstractModel {
    private final FireBaseManager mFirebaseManager;
    private final FirebaseAuth mAuth;

    public MainModel(){
        mFirebaseManager = FireBaseManager.getInstance();
        mAuth = mFirebaseManager.getAuth();
    }

    public boolean isAuthUser(){
        return mAuth.getCurrentUser() != null;
    }

    public void createUser(String email, String password) {
        mFirebaseManager.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                    } else {

                    }
                });
    }

    private Observable<FirebaseUser> createUserObs(String email, String pass){
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()){
                try {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            subscriber.onNext(mAuth.getCurrentUser());
                            subscriber.onCompleted();
                        }
                    }).addOnFailureListener(subscriber::onError);
                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    private Observable<FirebaseUser> signInUserObs(String email, String pass){
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()){
                try {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            subscriber.onNext(mAuth.getCurrentUser());
                            subscriber.onCompleted();
                        }
                    }).addOnFailureListener(subscriber::onError);
                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }
}
