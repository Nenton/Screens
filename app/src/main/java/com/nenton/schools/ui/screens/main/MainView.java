package com.nenton.schools.ui.screens.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.nenton.schools.R;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;
import com.nenton.schools.mvp.views.IMainView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by serge on 08.11.2017.
 */

class MainView extends AbstractView<MainScreen.MainPresenter> implements IMainView{

    @BindView(R.id.main_sound_iv)
    ImageView mSound;
    @BindView(R.id.main_image_btn)
    Button mImage;
    @BindView(R.id.main_product_name_tv)
    TextView mProductName;
    @BindView(R.id.main_product_description_tv)
    TextView mProductDescription;
    @BindView(R.id.main_help_product_iv)
    ImageView mHelp;
    @BindView(R.id.main_purchase_ib)
    ImageButton mPurchase;
    @BindView(R.id.main_exit_shop_ib)
    ImageButton mExitShop;
    @BindView(R.id.main_stuff_iv)
    ImageView mStuff;
    @BindView(R.id.main_privacy_policy_switch)
    SwitchButton mPrivacyPolicySwitch;
    @BindView(R.id.main_reload_ib)
    ImageButton mReload;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @OnClick(R.id.main_purchase_ib)
    void clickOnPurchase(){
        mPresenter.clickOnPurchase();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<MainScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void initView() {

    }
}
