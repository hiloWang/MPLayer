package com.hhly.lawyer.ui.view.two;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.base.BaseFragment;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class ExpenseOrdersFragment extends BaseFragment {

    private static final String FRAGMENT_SAVED_STATE_KEY = ExpenseOrdersFragment.class.getSimpleName();
    @BindView(R.id.textview1)
    TextView textview1;
    @BindView(R.id.textview2)
    TextView textview2;
    @BindView(R.id.textview3)
    TextView textview3;
    /**
     * 屏幕旋转,或者其他事件,导致fragment重走生命周期方法,那么recycler的decration将会变形,要重新设置 BorderDividerItemDecration;
     */
    private boolean resestTheLifeCycle;

    public static ExpenseOrdersFragment newInstance() {
        ExpenseOrdersFragment fragment = new ExpenseOrdersFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exponse;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        textview1.setText("计算器1");
        textview2.setText("计算器2");
        textview3.setText("计算器3");
    }

    @Override
    protected void initListeners() {
        RxView.clicks(textview1).throttleFirst(1, TimeUnit.SECONDS).subscribe(this::goTocalc1);
        RxView.clicks(textview2).throttleFirst(1, TimeUnit.SECONDS).subscribe(this::goTocalc2);
        RxView.clicks(textview3).throttleFirst(1, TimeUnit.SECONDS).subscribe(this::goTocalc3);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void mOnViewStateRestored(Bundle savedInstanceState) {
        savedInstanceState.getSerializable(FRAGMENT_SAVED_STATE_KEY);
        resestTheLifeCycle = true;
    }

    @Override
    protected void mOnSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(FRAGMENT_SAVED_STATE_KEY, null);
    }

    private void checkTheLifeCycleIsChanging(boolean resestTheLifeCycle) {
        if (resestTheLifeCycle) {
            this.resestTheLifeCycle = false;
        }
    }

    private void goTocalc1(Void aVoid) {
        this.goToCalc(textview1);
    }

    private void goTocalc2(Void aVoid) {
        this.goToCalc(textview2);
    }

    private void goTocalc3(Void aVoid) {
        this.goToCalc(textview3);
    }

    private void goToCalc(View sharedCardView) {
        Intent targetActivity = new Intent(getActivity(), CalcActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedCardView,
                    "sharedCardView");
            Bundle bundle = activityOptions.toBundle();
            if (sharedCardView instanceof TextView)
                bundle.putString("title", ((TextView) sharedCardView).getText().toString());
            else bundle.putString("title", "");
            targetActivity.putExtras(bundle);
            startActivity(targetActivity, bundle);
        } else {
            startActivity(targetActivity);
        }
    }
}
