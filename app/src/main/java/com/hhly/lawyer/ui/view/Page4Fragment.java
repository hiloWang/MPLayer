package com.hhly.lawyer.ui.view;

import android.os.Bundle;
import android.view.View;

import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.base.BaseFragment;

public class Page4Fragment extends BaseFragment {

    private static final String FRAGMENT_SAVED_STATE_KEY = HomeFragment.class.getSimpleName();
    /**
     * 屏幕旋转,或者其他事件,导致fragment重走生命周期方法,那么recycler的decration将会变形,要重新设置 BorderDividerItemDecration;
     */
    private boolean resestTheLifeCycle;

    public static Page4Fragment newInstance() {
        Page4Fragment fragment = new Page4Fragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page4;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

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
}
