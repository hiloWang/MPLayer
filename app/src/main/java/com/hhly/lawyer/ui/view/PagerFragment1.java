package com.hhly.lawyer.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.base.BaseFragment;
import com.hhly.lawyer.widget.ArrowItemView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class PagerFragment1 extends BaseFragment {

    public static final String ARGS_PAGE = "args_page";
    @BindView(R.id.sivTest)
    ArrowItemView sivTest;
    @BindView(R.id.bottom_sheet)
    NestedScrollView bottomSheet;
    @BindView(R.id.gmail_fab)
    FloatingActionButton fab;
    private int page;

    @BindView(R.id.textView)
    TextView textView;

    private BottomSheetBehavior bottomSheetBehavior;
    private Animation growAnimation;
    private Animation shrinkAnimation;
    private boolean showFAB;

    public static PagerFragment1 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        PagerFragment1 fragment = new PagerFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pager1;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        page = getArguments().getInt(ARGS_PAGE);
        textView.setText("第" + page + "页");
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        growAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_grow);
        shrinkAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_shrink);
    }

    @Override
    protected void initListeners() {
        RxView.clicks(textView).throttleFirst(1, TimeUnit.SECONDS).subscribe(aVoid -> {
            startActivity(new Intent(getActivity(), RegiestActivity.class));
        });

        RxView.clicks(sivTest).throttleFirst(1, TimeUnit.SECONDS).subscribe(this::showBottomSheet);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    // 被拖拽状态
                    case BottomSheetBehavior.STATE_DRAGGING:
                        fab.startAnimation(shrinkAnimation);
                        fab.setVisibility(View.GONE);
                        break;
                    // 隐藏状态。默认是false，可通过app:behavior_hideable属性设置。
                    case BottomSheetBehavior.STATE_HIDDEN:

                        break;

                    // 完全展开的状态
                    case BottomSheetBehavior.STATE_EXPANDED:
                        showFAB = true;
                        if (fab.getVisibility() == View.GONE) {
                            fab.setVisibility(View.VISIBLE);
                            fab.startAnimation(growAnimation);
                        }

                        break;

                    // 拖拽松开之后到达终点位置（collapsed or expanded）前的状态
                    case BottomSheetBehavior.STATE_SETTLING:

                        break;

                    // 折叠状态。可通过app:behavior_peekHeight来设置默认显示的高度
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        if (showFAB) {
                            showFAB = false;
                            fab.setVisibility(View.GONE);
                        }
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void mOnViewStateRestored(Bundle savedInstanceState) {

    }

    @Override
    protected void mOnSaveInstanceState(Bundle savedInstanceState) {

    }

    public void showBottomSheet(Void aVoid) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        else bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}
