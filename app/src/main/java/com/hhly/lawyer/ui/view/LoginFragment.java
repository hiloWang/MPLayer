package com.hhly.lawyer.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hhly.lawyer.R;
import com.hhly.lawyer.data.util.Logger;
import com.hhly.lawyer.di.components.MainComponent;
import com.hhly.lawyer.ui.base.BaseFragment;
import com.hhly.lawyer.ui.contract.LoginView;
import com.hhly.lawyer.ui.presenter.LoginPresenter;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

public class LoginFragment extends BaseFragment implements LoginView {

    private static final String FRAGMENT_SAVED_STATE_KEY = HomeFragment.class.getSimpleName();
    /**
     * 屏幕旋转,或者其他事件,导致fragment重走生命周期方法,那么recycler的decration将会变形,要重新设置 BorderDividerItemDecration;
     */
    private boolean resestTheLifeCycle;

    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvForgotPsd)
    TextView tvForgotPsd;
    @BindView(R.id.tvRegiest)
    TextView tvRegiest;
    @Inject
    LoginPresenter presenter;
    MainComponent mainComponent;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }


    @Override
    protected void initListeners() {
        RxView.clicks(btnLogin).throttleFirst(1, TimeUnit.SECONDS).subscribe(this::bringToActivity);
        RxView.clicks(tvForgotPsd).throttleFirst(1, TimeUnit.SECONDS).subscribe(this::bringToForgotYourPasswordActivity);
        RxView.clicks(tvRegiest).throttleFirst(1, TimeUnit.SECONDS).subscribe(this::bringToRegiestActivity);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initInjector() {
        mainComponent = getComponent(MainComponent.class);
        mainComponent.inject(this);
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
    public void setUsernameError() {
        etUsername.requestFocus();
        etUsername.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        etPassword.requestFocus();
        etPassword.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToHome() {
        Logger.e("navigateToHome");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void serverError() {

    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    private void bringToActivity(Void aVoid) {
        presenter.validateCredentials(etUsername.getText().toString(), etPassword.getText().toString());
    }

    private void bringToForgotYourPasswordActivity(Void aVoid) {
        startActivity(new Intent(getActivity(), ForgotYourPasswordActivity.class));
    }

    private void bringToRegiestActivity(Void aVoid) {
        startActivity(new Intent(getActivity(), RegiestActivity.class));
    }
}
