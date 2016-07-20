package com.hhly.lawyer.ui.view;

import android.os.Bundle;
import android.view.View;

import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.base.BaseFragment;

public class Page2Fragment extends BaseFragment {

    private static final String FRAGMENT_SAVED_STATE_KEY = Page2Fragment.class.getSimpleName();
    /**
     * 屏幕旋转,或者其他事件,导致fragment重走生命周期方法,那么recycler的decration将会变形,要重新设置 BorderDividerItemDecration;
     */
    private boolean resestTheLifeCycle;

    public static Page2Fragment newInstance() {
        Page2Fragment fragment = new Page2Fragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_page2;
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
