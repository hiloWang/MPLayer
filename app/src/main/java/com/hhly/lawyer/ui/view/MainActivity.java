package com.hhly.lawyer.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hhly.lawyer.R;
import com.hhly.lawyer.adapter.FragmentPagerAdapter;
import com.hhly.lawyer.di.components.DaggerMainComponent;
import com.hhly.lawyer.di.components.MainComponent;
import com.hhly.lawyer.di.interfaces.HasComponent;
import com.hhly.lawyer.ui.base.BaseDrawerLayoutActivity;
import com.hhly.lawyer.widget.UnScrollableViewPager;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.BindView;
import butterknife.BindViews;

public class MainActivity extends BaseDrawerLayoutActivity implements HasComponent<MainComponent> {

    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4.0f);
    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();

    @BindViews({R.id.rb1, R.id.rb2, R.id.rb3, R.id.rb4})
    RadioButton[] radioButtons;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.viewPager)
    UnScrollableViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.cloud)
    TextView tvCloud;
    @BindView(R.id.llFabMenuContainerFirst)
    LinearLayout llFabMenuContainerFirst;
    @BindView(R.id.tvFabMenuActionFirst)
    CardView tvFabMenuActionFirst;
    @BindView(R.id.ivFabMenuActionFirst)
    ImageView ivFabMenuActionFirst;

    private MainComponent mainComponent;
    private boolean fabOpened;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        mainComponent = DaggerMainComponent.builder().activityModule(getActivityModule()).appComponent(getAppComponent()).build();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.initializeViewPager();
        this.initializeRadioGroup(savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {
        RxView.clicks(fab).subscribe(this::onFabClicked);
        RxView.clicks(llFabMenuContainerFirst).subscribe(this::onFabMenuClicked);
        tvCloud.setOnClickListener(v -> {
            if (fabOpened) closeFabMenu();
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb1:
                    viewPager.setCurrentItem(0);
                    showToast("1");
                    break;
                case R.id.rb2:
                    viewPager.setCurrentItem(1);
                    showToast("2");
                    break;
                case R.id.rb3:
                    viewPager.setCurrentItem(2);
                    showToast("3");
                    break;
                case R.id.rb4:
                    viewPager.setCurrentItem(3);
                    showToast("4");
                    break;
            }
        });
    }

    @Override
    protected NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return item -> MainActivity.this.menuItemChecked(item.getItemId());
    }

    @Override
    protected int[] getMenuItemIds() {
        return new int[]{R.id.nav_home};
    }

    @Override
    protected void onMenuItemOnClick(MenuItem now) {

    }

    @Override
    public MainComponent getComponent() {
        return mainComponent;
    }


    private void initializeViewPager() {
        viewPager.setOffscreenPageLimit(4);
        Fragment[] fragments = {
                HomeFragment.newInstance(),
                Page2Fragment.newInstance(),
                Page3Fragment.newInstance(),
                Page4Fragment.newInstance()
        };
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
    }

    private void initializeRadioGroup(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            radioGroup.check(R.id.rb1);
        }
    }

    private void onFabClicked(Void view) {
        if (!fabOpened) openFabMenu();
        else closeFabMenu();
    }

    private void onFabMenuClicked(Void view) {
        this.showSnackbar();
        this.closeFabMenu();
    }


    private void openFabMenu() {
        // the menu is opening about fab
        fabOpened = true;
        fab.setEnabled(false);
        // fab animation
        this.openFabAnimation(fab);
        // background cloud animation
        this.openFogEffectAnimation();
        // fabMenu action animation
        this.openFabMenuAnimation();
    }

    private void closeFabMenu() {
        // the menu is closing about fab
        fabOpened = false;
        fab.setEnabled(false);

        // fab animation
        this.closeFabAnimation(fab);
        // background cloud animation
        this.closeFogEffectAnimation();
        // fabMenu action animation
        this.closeFabMenuAnimation();
    }

    private void closeFabMenuAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(ivFabMenuActionFirst, "scaleX", 1.0f, 0.8f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(DECELERATE_INTERPOLATOR);
        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(ivFabMenuActionFirst, "scaleY", 1.0f, 0.3f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(DECELERATE_INTERPOLATOR);
        ObjectAnimator alphaBounce = ObjectAnimator.ofFloat(ivFabMenuActionFirst, "alpha", 1.f, 0.f);
        alphaBounce.setDuration(300);

        ObjectAnimator translationX = ObjectAnimator.ofFloat(tvFabMenuActionFirst, "translationX", 0f, 90f);
        translationX.setDuration(200);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(tvFabMenuActionFirst, "alpha", 1.f, 0.f);
        alphaAnim.setInterpolator(DECELERATE_INTERPOLATOR);
        alphaAnim.setDuration(300);

        animatorSet.playTogether(translationX, alphaAnim, bounceAnimX, bounceAnimY, alphaBounce);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tvFabMenuActionFirst.setVisibility(View.INVISIBLE);
                ivFabMenuActionFirst.setVisibility(View.INVISIBLE);
                fab.setEnabled(true);
            }
        });
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    private void closeFogEffectAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.85f, 0);
        alphaAnimation.setDuration(250);
        tvCloud.startAnimation(alphaAnimation);
        tvCloud.setVisibility(View.GONE);
    }

    private void closeFabAnimation(View fab) {
        llFabMenuContainerFirst.setVisibility(View.INVISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab, "rotation", -135, -175, 0);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(DECELERATE_INTERPOLATOR);
        objectAnimator.start();
    }

    private void openFabMenuAnimation() {
        llFabMenuContainerFirst.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        tvFabMenuActionFirst.setVisibility(View.VISIBLE);
        ivFabMenuActionFirst.setVisibility(View.VISIBLE);
        tvFabMenuActionFirst.setAlpha(1.f);
        ivFabMenuActionFirst.setAlpha(1.f);

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(ivFabMenuActionFirst, "scaleX", 0.8f, 1.f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);
        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(ivFabMenuActionFirst, "scaleY", 0.8f, 1.f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator translateAnimX = ObjectAnimator.ofFloat(tvFabMenuActionFirst, "translationX", 170f, -15f, 0f);
        translateAnimX.setDuration(300);

        animatorSet.playTogether(bounceAnimX, bounceAnimY, translateAnimX);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fab.setEnabled(true);
            }
        });
        animatorSet.setDuration(200);
        animatorSet.start();
    }

    private void openFogEffectAnimation() {
        tvCloud.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.85f);
        alphaAnimation.setDuration(250);
        alphaAnimation.setFillAfter(true);
        tvCloud.startAnimation(alphaAnimation);
    }

    private void openFabAnimation(View fab) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab, "rotation", 0, -155, -135);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    private void showSnackbar() {
        Snackbar snackbar = Snackbar.make(fab, "the fab menu clicked :)", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setBackgroundColor(getResources().getColor(R.color.background_layout));
        ((TextView) snackbarLayout.findViewById(R.id.snackbar_text)).setTextColor(
                getResources().getColor(R.color.design_black_text));
        snackbar.show();
    }
}
