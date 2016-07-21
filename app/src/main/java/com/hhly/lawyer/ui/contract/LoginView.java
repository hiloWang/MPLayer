package com.hhly.lawyer.ui.contract;

import com.hhly.lawyer.ui.LoadingDataView;

public interface LoginView extends LoadingDataView {

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void showProgress();

    void hideProgress();

    void serverError();
}
