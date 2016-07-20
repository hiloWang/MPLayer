package com.hhly.lawyer.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hhly.lawyer.App;
import com.hhly.lawyer.R;
import com.hhly.lawyer.data.di.components.ApiComponent;
import com.hhly.lawyer.di.components.AppComponent;
import com.hhly.lawyer.di.modules.ActivityModule;

import butterknife.ButterKnife;

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getLayoutId() != -1)
            setContentView(getLayoutId());
        ButterKnife.bind(this);

        this.getAppComponent().inject(this);
        this.initToolbarOnCreate(savedInstanceState);
        this.initInjector();
        this.initViews(savedInstanceState);
        this.initListeners();
        this.initData();
    }

    /**
     * Fill in layout id
     *
     * @return layout id
     */
    protected abstract int getLayoutId();

    /**
     * Initialize the toolbar in the layout
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initToolbarOnCreate(Bundle savedInstanceState);

    /**
     * Initialize the Injector to Activity
     */
    protected abstract void initInjector();

    /**
     * Initialize the view in the layout
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * Initialize the Activity data
     */
    protected abstract void initData();

    /**
     * Initialize the View of the listener
     */
    protected abstract void initListeners();

    /**
     * Call this when your activity is done and should be closed.  The
     * ActivityResult is propagated back to whoever launched you via
     * onActivityResult().
     */
    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void showToast(String msg) {
        getAppComponent().toast().makeText(msg);
    }

    protected void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            this.getAppComponent().toast().makeText(msg, duration);
        } else {
            this.showToast(msg);
        }
    }

    protected void showToast(int resId) {
        getAppComponent().toast().makeText(resId);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return AppComponent
     */
    public AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    public ApiComponent getApiComponent() {
        return ((App) getApplication()).getApiComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return Activity Module
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected void overridePendingTransition(boolean backActivity) {
        if (backActivity) overridePendingTransition(0, R.anim.activity_swipeback_ac_right_out);
        else overridePendingTransition(R.anim.activity_swipeback_ac_right_in,
                R.anim.activity_swipeback_ac_right_remain);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(true);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        this.overridePendingTransition(false);
    }
}
