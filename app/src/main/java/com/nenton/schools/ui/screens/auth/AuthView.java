package com.nenton.schools.ui.screens.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by serge on 07.11.2017.
 */

public class AuthView extends AbstractView<AuthScreen.AuthPresenter> {

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
    @BindView(R.id.login_image_btn)
    Button mLoginBtn;

    @BindView(R.id.login_full_name_wrap)
    LinearLayout mFullNameWrap;

    @OnClick(R.id.login_image_btn)
    public void clickLogin() {
        if (mLoginBtn.getText().equals("Login")){
            mPresenter.clickLogin(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
        } else if (mLoginBtn.getText().equals("Create user")){
            mPresenter.clickCreateUser(mEmailEt.getText().toString(), mPasswordEt.getText().toString(), mFullNameEt.getText().toString());
        }
    }

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<AuthScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void initView() {
        mTypeAccount.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.new_account_rb) {
                mPresenter.changeState(mPresenter.STATE_NEW_ACCOUNT);
            } else if (i == R.id.existing_account_rb) {
                mPresenter.changeState(mPresenter.STATE_EXISTING_ACCOUNT);
            }
        });
        mTypeAccount.check(R.id.existing_account_rb);
    }

    public void showNewAccountState() {
        mForgotPasswordBtn.setVisibility(GONE);
        mFullNameWrap.setVisibility(VISIBLE);
        mLoginBtn.setText("Create user");
    }

    public void showExistingAccountState() {
        mForgotPasswordBtn.setVisibility(VISIBLE);
        mFullNameWrap.setVisibility(GONE);
        mLoginBtn.setText("Login");
    }
}
