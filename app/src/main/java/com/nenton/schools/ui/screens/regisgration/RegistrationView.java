package com.nenton.schools.ui.screens.regisgration;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.nenton.schools.R;
import com.nenton.schools.data.storage.dto.UserDto;
import com.nenton.schools.data.storage.realm.UserRealm;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;

import butterknife.BindView;

/**
 * Created by serge on 08.11.2017.
 */

public class RegistrationView extends AbstractView<RegistrationScreen.RegistrationPresenter> {

    @BindView(R.id.reg_type_user_rg)
    RadioGroup mTypeUser;
    @BindView(R.id.reg_user_name)
    EditText mUserName;
    @BindView(R.id.reg_user_email)
    EditText mUserEmail;
    @BindView(R.id.reg_user_id)
    EditText mUserId;

    @BindView(R.id.reg_user_grade)
    Button mUserGrage;
    @BindView(R.id.reg_classrooms)
    Button mClassroom;
    @BindView(R.id.reg_logout)
    Button mLogout;
    @BindView(R.id.reg_user_state)
    Button mUserState;
    @BindView(R.id.reg_user_district)
    Button mUserDistrict;

    @BindView(R.id.reg_type_education_rg)
    RadioGroup mTypeEducation;
    @BindView(R.id.reg_education)
    Button mEducation;
    @BindView(R.id.reg_add_school)
    ImageButton mAddSchool;
    @BindView(R.id.reg_geo)
    ImageButton mGeo;
    @BindView(R.id.reg_phone)
    ImageButton mPhone;
    @BindView(R.id.reg_description_et)
    EditText mDescription;

    public RegistrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<RegistrationScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void initView(UserRealm user) {
        mUserName.setText(user.getName());
        mUserEmail.setText(user.getEmail());
        mUserId.setText(user.getId());

        mUserGrage.setText(user.getSchoolType());
        mUserState.setText(user.getState());
        mUserDistrict.setText(user.getSchoolDistrict());

        mDescription.setText(user.getSchoolStreet());
    }
}
