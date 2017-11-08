package com.nenton.schools.ui.screens.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by serge on 07.11.2017.
 */

public class MainView extends AbstractView<MainScreen.MainPresenter> {

    @BindView(R.id.login_cancel_btn)
    Button mCancelBtn;
    @BindView(R.id.login_next_btn)
    Button mNextBtn;
    @BindView(R.id.login_email_et)
    EditText mEmailEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;
    @BindView(R.id.login_forgot_password_btn)
    Button mForgotPasswordBtn;
    @BindView(R.id.login_full_name_et)
    EditText mFullNameEt;
    @BindView(R.id.login_type_account)
    RadioGroup mTypeAccount;

    @OnClick(R.id.login_image_btn)
    public void clickLogin(){
        mPresenter.clickLogin(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<MainScreen.Component>getDaggerComponent(context).inject(this);
    }
}
