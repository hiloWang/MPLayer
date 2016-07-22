package com.hhly.lawyer.ui.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhly.lawyer.R;
import com.hhly.lawyer.adapter.MyFragmentPagerAdapter;
import com.hhly.lawyer.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;

public class TabLayoutFragment extends BaseFragment {

    private static final String FRAGMENT_SAVED_STATE_KEY = HomeFragment.class.getSimpleName();

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    /**
     * 屏幕旋转,或者其他事件,导致fragment重走生命周期方法,那么recycler的decration将会变形,要重新设置 BorderDividerItemDecration;
     */
    private boolean resestTheLifeCycle;

    private MyFragmentPagerAdapter pagerAdapter;

    public static TabLayoutFragment newInstance() {
        TabLayoutFragment fragment = new TabLayoutFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tablayout;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        if (pagerAdapter == null)
            pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
