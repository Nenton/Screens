package com.nenton.schools.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.nenton.schools.BuildConfig;
import com.nenton.schools.R;
import com.nenton.schools.data.managers.DataManager;
import com.nenton.schools.data.managers.FireBaseManager;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.di.components.AppComponent;
import com.nenton.schools.di.modules.PicassoCacheModule;
import com.nenton.schools.di.modules.RootModule;
import com.nenton.schools.di.sqopes.RootScope;
import com.nenton.schools.flow.AbstractScreen;
import com.nenton.schools.flow.TreeKeyDispatcher;
import com.nenton.schools.mvp.presenters.RootPresenter;
import com.nenton.schools.mvp.views.IRootView;
import com.nenton.schools.mvp.views.IView;
import com.nenton.schools.ui.screens.auth.AuthScreen;
import com.nenton.schools.ui.screens.main.MainScreen;
import com.nenton.schools.ui.screens.regisgration.RegistrationScreen;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;
import rx.subscriptions.CompositeSubscription;

public class RootActivity extends AppCompatActivity implements IRootView {

    @Inject
    RootPresenter mRootPresenter;
    @BindView(R.id.root_frame)
    FrameLayout mFrameContainer;
    @BindView(R.id.drawer_root)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        ButterKnife.bind(this);
        RootComponent rootComponent = DaggerService.getDaggerComponent(this);
        rootComponent.inject(this);
        mRootPresenter.takeView(this);
        initBottomNavView();
        disableShiftMode(mBottomNavigationView);
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    private void initBottomNavView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Object key = null;
            switch (item.getItemId()) {
//                case R.id.action_home:
////                    key = new AuthScreen();
//                    break;
            }

            if (key != null) {
                Flow.get(RootActivity.this).set(key);
            }
            return true;
        });
    }

    @Override
    public void changeOnBottom(@IdRes int id) {
        mBottomNavigationView.setSelectedItemId(id);
    }

    @Override
    public void checkBottomNavView(View view) {
//        if (view instanceof MainView){
//            mBottomNavigationView.setSelectedItemId(R.id.action_home);
//        }
//        if (view instanceof AccountView){
//            mBottomNavigationView.setSelectedItemId(R.id.action_account);
//        }
//        if (view instanceof AddPhotocardView){
//            mBottomNavigationView.setSelectedItemId(R.id.action_upload);
//        }
    }

    public void stateBottomNavView(boolean is) {
        if (is) {
            mBottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            mBottomNavigationView.setVisibility(View.GONE);
        }
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
        AbstractScreen key = new AuthScreen();
        if (FireBaseManager.getInstance().getFirebaseAuth().getCurrentUser() != null) {
            if (DataManager.getInstance().checkCompleteRegistration()) {
                key = new MainScreen();
            } else {
                key = new RegistrationScreen();
            }
        }

        newBase = Flow.configure(newBase, this)
                .defaultKey(key)
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

    @dagger.Component(dependencies = AppComponent.class, modules = {RootModule.class, PicassoCacheModule.class})
    @RootScope
    public interface RootComponent {
        void inject(RootActivity rootActivity);

        void inject(RootPresenter rootPresenter);

        RootPresenter getRootPresenter();

        Picasso getPicasso();
    }
    //endregion
}
