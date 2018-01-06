package com.nenton.schools.ui.screens.history;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nenton.schools.R;
import com.nenton.schools.data.storage.realm.EntityHistoryPassRealm;
import com.nenton.schools.di.DaggerService;
import com.nenton.schools.mvp.views.AbstractView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by serge on 22.11.2017.
 */

public class HistoryView extends AbstractView<HistoryScreen.HistoryPresenter> {

    @BindView(R.id.history_rv)
    RecyclerView mRecyclerView;

    private HistoryAdapter mAdapter;

    public HistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<HistoryScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new HistoryAdapter();
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void addEntities(RealmList<EntityHistoryPassRealm> entity) {
        mAdapter.addEntity(entity);
    }
}
