package com.nenton.schools.ui.screens.schoolPass;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.nenton.schools.R;
import com.nenton.schools.data.storage.dto.RoomsOfSchool;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;
import com.nenton.schools.ui.custom_views.CustomChronometer;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by serge on 15.11.2017.
 */

class SchoolPassView extends AbstractView<SchoolPassScreen.SchoolPassPresenter> {

    @BindView(R.id.pass_log_et)
    EditText mLog;
    @BindView(R.id.pass_passes_tv)
    TextView mPasses;
    @BindView(R.id.pass_district_data_et)
    EditText mPassDate;
    @BindView(R.id.pass_current_room_et)
    EditText mCurrentRoom;
    @BindView(R.id.pass_teacher_et)
    EditText mTeacher;

    @BindViews({R.id.pass_restroom_sb, R.id.pass_counselor_sb, R.id.pass_main_office_sb, R.id.pass_student_service_sb, R.id.pass_library_sb, R.id.pass_clinic_sb, R.id.pass_other_sb})
    List<SwitchButton> mSwitches;
    @BindView(R.id.pass_restroom_sb)
    SwitchButton mSwitchRestroom;
    @BindView(R.id.pass_counselor_sb)
    SwitchButton mSwitchCounselor;
    @BindView(R.id.pass_main_office_sb)
    SwitchButton mSwitchMainOffice;
    @BindView(R.id.pass_student_service_sb)
    SwitchButton mSwitchStudentService;
    @BindView(R.id.pass_library_sb)
    SwitchButton mSwitchLibrary;
    @BindView(R.id.pass_clinic_sb)
    SwitchButton mSwitchClinic;
    @BindView(R.id.pass_other_sb)
    SwitchButton mSwitchOther;

    @BindView(R.id.pass_chalkboard_iv)
    ImageView mChalkboard;
    @BindView(R.id.pass_chronometer_tc)
    CustomChronometer mChronometer;
    @BindView(R.id.pass_classes_iv)
    ImageView mClassesImage;
    @BindView(R.id.pass_shop_iv)
    ImageView mShopImage;
    @BindView(R.id.pass_details_et)
    EditText mDetails;

    @OnCheckedChanged(R.id.pass_restroom_sb)
    void checkedRestroom() {
        mPresenter.changeSwitch(R.id.pass_restroom_sb);
    }

    @OnCheckedChanged(R.id.pass_main_office_sb)
    void checkedMainOffice() {
        mPresenter.changeSwitch(R.id.pass_main_office_sb);
    }

    @OnClick(R.id.pass_chalkboard_iv)
    void clickOnChalkboard() {
        mPresenter.clickOnChalkboard();
    }

    @OnClick(R.id.pass_classes_iv)
    void clickOnChoiseClass() {
        mPresenter.clickOnChoiseClass();
    }

    public CustomChronometer getChronometer() {
        return mChronometer;
    }

    public SchoolPassView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<SchoolPassScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void showOnlyRestroom() {
        if (mSwitchRestroom.isChecked()) {
            for (SwitchButton switchButton : mSwitches) {
                switchButton.setChecked(false);
            }
            mSwitchRestroom.setChecked(true);
        }
    }

    public void showOnlyMainOffice() {
        if (mSwitchMainOffice.isChecked()) {
            for (SwitchButton switchButton : mSwitches) {
                switchButton.setChecked(false);
            }
            mSwitchMainOffice.setChecked(true);
        }
    }

    public void initView() {

    }

    public void disableSwitches() {
        for (SwitchButton switchButton : mSwitches) {
            switchButton.setEnabled(false);
        }
    }

    public void enableSwitches() {
        for (SwitchButton switchButton : mSwitches) {
            switchButton.setEnabled(true);
        }
    }

    public void showPickerRoomNameAndTeacher(String[] strings) {
        NumberPicker numberPicker = new NumberPicker(getContext());
        numberPicker.setDisplayedValues(strings);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(strings.length - 1);
        numberPicker.getValue();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose State")
                .setView(numberPicker)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    mPresenter.clickOnRoom(i);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .create()
                .show();
    }

    public void fillRoom(String name, String teacher) {
        mCurrentRoom.setText(name);
        mTeacher.setText(teacher);
    }
}
