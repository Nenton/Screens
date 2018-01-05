package com.nenton.schools.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.nenton.schools.BuildConfig;
import com.nenton.schools.R;
import com.nenton.schools.data.managers.FireBaseManager;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.flow.TreeKeyDispatcher;
import com.nenton.schools.mortar.ScreenScoper;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.mvp.views.IRootView;
import com.nenton.schools.mvp.views.IView;
import com.nenton.schools.ui.screens.auth.AuthScreen;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class LoginActivity extends AppCompatActivity implements IRootView {
    public static final String TAG = "LoginActivity";

    @Inject
    RootPresenter mRootPresenter;

    @BindView(R.id.root_frame)
    FrameLayout mRootFrame;
    @BindView(R.id.frame_progress_bar)
    FrameLayout mProgressBarFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (FireBaseManager.getInstance().getFirebaseAuth().getCurrentUser() != null) {
            startRootActivity();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState != null) {
            BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(savedInstanceState);
        }
        ButterKnife.bind(this);

        DaggerService.<RootActivity.RootComponent>getDaggerComponent(this).inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        Log.e(TAG, "getSystemService: " + name);
        MortarScope splashActivityScope = MortarScope.findChild(getApplicationContext(), RootActivity.class.getName());
        return splashActivityScope.hasService(name) ? splashActivityScope.getService(name) : super.getSystemService(name);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new AuthScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            ScreenScoper.destroyScreenScope(AuthScreen.class.getName());
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mRootPresenter.takeView(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mRootPresenter.dropView(this);
        super.onPause();
    }

    //region -----------IRootView----------

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        FirebaseCrash.log("ROOT VIEW EXCEPTION");
        FirebaseCrash.report(e);
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Something went wrong. Please try again later");
        }
    }

    @Override
    public void showLoad() {
        mProgressBarFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoad() {
        mProgressBarFrame.setVisibility(View.INVISIBLE);
    }


    @Nullable
    @Override
    public IView getCurrentScreen() {
        return (IView) mRootFrame.getChildAt(0);
    }

    //endregion

    public void startRootActivity() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        finish();
    }

}
