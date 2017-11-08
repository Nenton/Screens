package com.nenton.schools.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.nenton.schools.BuildConfig;
import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.components.AppComponent;
import com.nenton.schools.di.modules.AuthModule;
import com.nenton.schools.di.modules.PicassoCacheModule;
import com.nenton.schools.di.sqopes.AuthScope;
import com.nenton.schools.flow.TreeKeyDispatcher;
import com.nenton.schools.mvp.presenters.AuthPresenter;
import com.nenton.schools.mvp.views.IAuthView;
import com.nenton.schools.mvp.views.IView;
import com.nenton.schools.ui.screens.main.MainScreen;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class AuthActivity extends AppCompatActivity implements IAuthView{

    @Inject
    AuthPresenter mRootPresenter;
    @BindView(R.id.root_frame)
    FrameLayout mFrameContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        ButterKnife.bind(this);
        AuthActivity.AuthComponent rootComponent = DaggerService.getDaggerComponent(this);
        rootComponent.inject(this);
        mRootPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        mRootPresenter.dropView(this);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new MainScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();
        super.attachBaseContext(newBase);
    }

    @Override
    public Object getSystemService(String name) {
        MortarScope rootActivityScope = MortarScope.findChild(getApplicationContext(), RootActivity.class.getName());
        return rootActivityScope.hasService(name) ? rootActivityScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        FirebaseCrash.log("ROOT VIEW EXCEPTION");
        FirebaseCrash.report(e);
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Что-то пошло не так. Попробуйте повторить позже"); // TODO: 07.11.2017 поменятть на англ
        }
    }

    @Override
    public void showLoad() {
// TODO: 07.11.2017 реализовать

    }

    @Override
    public void hideLoad() {
// TODO: 07.11.2017 реализовать
    }


    @Nullable
    @Override
    public IView getCurrentScreen() {
        return (IView) mFrameContainer.getChildAt(0);
    }


    @Override
    public void onBackPressed() {
        if (getCurrentScreen() != null && !getCurrentScreen().viewOnBackPressed() && !Flow.get(this).goBack()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выход")
                    .setPositiveButton("Да", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Нет", (dialog, which) -> dialog.cancel())
                    .setMessage("Вы действительно хотите выйти?")
                    .show();
        }
    }

    public boolean isAllGranted(@NonNull String[] permissions, boolean allGranted) {
        for (String permission : permissions) {
            int selfPermission = ContextCompat.checkSelfPermission((this), permission);
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRootPresenter.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRootPresenter.onActivityResult(requestCode, resultCode, data);
    }

    //region ========================= DI =========================

    @dagger.Component(dependencies = AppComponent.class, modules = AuthModule.class)
    @AuthScope
    public interface AuthComponent {
        void inject(AuthActivity authActivity);

        void inject(AuthPresenter authPresenter);

        AuthPresenter getAuthPresenter();
    }
    //endregion
}
