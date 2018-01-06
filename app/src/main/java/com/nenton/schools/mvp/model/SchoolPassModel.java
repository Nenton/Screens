package com.nenton.schools.mvp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nenton.schools.data.managers.FireBaseManager;
import com.nenton.schools.data.storage.dto.EntityHistoryPass;
import com.nenton.schools.data.storage.realm.RoomRealm;

import java.util.HashMap;
import java.util.List;

/**
 * Created by serge on 15.11.2017.
 */

public class SchoolPassModel extends AbstractModel{

    private final FireBaseManager mFirebaseManager;
    private final FirebaseAuth mAuth;
    private final FirebaseDatabase mDB;

    public SchoolPassModel() {
        mFirebaseManager = FireBaseManager.getInstance();
        mAuth = mFirebaseManager.getFirebaseAuth();
        mDB = mFirebaseManager.getFirebaseDatabase();
    }

    public List<RoomRealm> getRoomsOfSchool(){
        mDataManager.saveRoomsOfSchool();
        return mDataManager.getRoomsOfSchool();
    }

    public void saveEntityToHistory(String room, String teacher, long start, long end) {
        if (mAuth.getCurrentUser() != null){
            String schoolId = mDataManager.getRealmManager().getSchoolByUser(mAuth.getCurrentUser().getUid()).getId();
            HashMap<String, Object> entity = new HashMap<>();
            entity.put("schoolId", schoolId);
            entity.put("room", room);
            entity.put("teacher", teacher);
            entity.put("start", start);
            entity.put("end", end);
            DatabaseReference push = mDB.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("history").push();
            push.updateChildren(entity).addOnCompleteListener(task -> {
                mDataManager.getRealmManager().saveHistory(new EntityHistoryPass(schoolId, room, teacher, start, end), push.getKey());
            });
        }
    }
}
