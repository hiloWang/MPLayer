package com.hhly.lawyer.ui.view;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.base.BaseToolbarActivity;
import com.hhly.lawyer.util.DateUtils;

import java.util.Date;

import butterknife.BindView;

public class CalcActivity extends BaseToolbarActivity {

    @BindView(R.id.nsTitle)
    TextView nsTitle;
    @BindView(R.id.tvSharedCardViewTitle)
    TextView tvSharedCardViewTitle;
    @BindView(R.id.tvSharedCardViewDate)
    TextView tvSharedCardViewDate;
    @BindView(R.id.circularRevealContainer)
    LinearLayout circularRevealContainer;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.coordiNatorContent)
    CoordinatorLayout coordiNatorContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calc;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ViewCompat.setElevation(appBarLayout, 0);
        this.setupToolbarTitle();
        this.setupSharedCardView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {
        circularRevealContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onPreDraw() {
                circularRevealContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        circularRevealContainer, 0, 0, 0,
                        (float) Math.hypot(circularRevealContainer.getWidth(), circularRevealContainer.getHeight()));
                animator.setDuration(700);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tvSharedCardViewTitle.setVisibility(View.GONE);
            tvSharedCardViewDate.setVisibility(View.GONE);
            this.finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }

    private void setupSharedCardView() {
        tvSharedCardViewTitle.setText("tvSharedCardViewTitle");
        tvSharedCardViewDate.setText(DateUtils.date2HHmm(new Date()));
    }

    private void setupToolbarTitle() {
        Bundle expenseOridersBundle = getIntent().getExtras();
        if (expenseOridersBundle != null) {
            mActionBarHelper.setTitle(getString(R.string.calc_title));
            nsTitle.setText(expenseOridersBundle.getString("title"));
        }
    }
}
