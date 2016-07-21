package com.hhly.lawyer.ui.contract;

import com.hhly.lawyer.ui.LoadingMvpView;

public interface LoginView extends LoadingMvpView {

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void showProgress();

    void hideProgress();

    void serverError();
}
