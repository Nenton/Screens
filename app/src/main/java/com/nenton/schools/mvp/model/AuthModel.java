package com.nenton.schools.mvp.model;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nenton.schools.data.managers.FireBaseManager;
import com.nenton.schools.data.storage.realm.UserRealm;

import rx.Observable;

/**
 * Created by serge on 07.11.2017.
 */

public class AuthModel extends AbstractModel {
    private final FireBaseManager mFirebaseManager;
    private final FirebaseAuth mAuth;

    public AuthModel() {
        mFirebaseManager = FireBaseManager.getInstance();
        mAuth = mFirebaseManager.getFirebaseAuth();
    }

    public boolean isAuthUser() {
        return mAuth.getCurrentUser() != null;
    }

    public Observable<FirebaseUser> createUserObs(String email, String pass) {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                try {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            subscriber.onNext(mAuth.getCurrentUser());
                            subscriber.onCompleted();
                        }
                    }).addOnFailureListener(subscriber::onError);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<FirebaseUser> signInUserObs(String email, String pass) {
        return Observable.create(subscriber -> {
            if (!subscriber.isUnsubscribed()) {
                try {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            subscriber.onNext(mAuth.getCurrentUser());
                            subscriber.onCompleted();
                        }
                    }).addOnFailureListener(subscriber::onError);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    @RxLogObservable
    public Observable<FirebaseUser> createUser(String email, String pass, String fullName) {
        return createUserObs(email, pass)
                .doOnNext(firebaseUser -> {
                    UserRealm userRealm = new UserRealm();
                    userRealm.setEmail(firebaseUser.getEmail());
                    userRealm.setId(firebaseUser.getUid());
                    userRealm.setName(fullName);
                    mFirebaseManager.getFirebaseDatabase().getReference().child("users").child(firebaseUser.getUid()).setValue(userRealm);
                });
    }

    @RxLogObservable
    public Observable<FirebaseUser> loginUser(String email, String pass) {
        return signInUserObs(email, pass);
    }

    public void saveUserInfo() {
        DatabaseReference user = mFirebaseManager.getFirebaseDatabase().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        if (user != null){
            user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserRealm userRealm = dataSnapshot.getValue(UserRealm.class);
                    mDataManager.saveUserToRealm(userRealm);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
