package com.nenton.schools.ui.screens.regisgration;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nenton.schools.R;
import com.nenton.schools.data.storage.realm.UserRealm;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.reg_complete_edit)
    ImageButton mCompleteButton;
    @BindView(R.id.reg_phone)
    ImageButton mPhone;
    @BindView(R.id.reg_school_position_et)
    EditText mSchoolPosition;

    public RegistrationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @OnClick(R.id.reg_complete_edit)
    public void clickComplete() {
        mPresenter.clickComplete();
    }

    @OnClick(R.id.reg_user_state)
    public void clickState() {
        mPresenter.clickState();
    }

    @OnClick(R.id.reg_user_district)
    public void clickDistrict() {
        mPresenter.clickDistrict();
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

        mSchoolPosition.setText(user.getSchoolStreet());
    }

    public String getName() {
        return mUserName.getText().toString();
    }

    public String getEmail() {
        return mUserEmail.getText().toString();
    }

    public String getGrade() {
        return mUserGrage.getText().toString();
    }

    public String getState() {
        return mUserState.getText().toString();
    }

    public String getDistrict() {
        return mUserDistrict.getText().toString();
    }

    public String getSchools() {
        return null; // TODO: 10.11.2017 implement me
    }

    public String getTypeEducation() {
        return ((RadioButton) findViewById(mTypeEducation.getCheckedRadioButtonId())).getText().toString();
    }

    public String getSchoolPosition() {
        return mSchoolPosition.getText().toString();
    }

    public void showPickerState() {
        NumberPicker numberPicker = new NumberPicker(getContext());
        String[] strings = new String[]{"First", "Second", "Three"};
        numberPicker.setDisplayedValues(strings);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(strings.length - 1);
        numberPicker.getValue();
//        numberPicker.setValue(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose State")
                .setView(numberPicker)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    mUserState.setText(strings[numberPicker.getValue()]);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .create()
                .show();
    }

    public void showPickerDistrict() {
        NumberPicker numberPicker = new NumberPicker(getContext());
        String[] strings = new String[]{"First", "Second", "Three"};
        numberPicker.setDisplayedValues(strings);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(strings.length - 1);
        numberPicker.getValue();
//        numberPicker.setValue(1);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose District")
                .setView(numberPicker)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    mUserDistrict.setText(strings[numberPicker.getValue()]);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .create()
                .show();
    }

    public void showNotCompleted() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Error")
                .setMessage("Registration not completed")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });
        builder.create()
                .show();
    }
}
