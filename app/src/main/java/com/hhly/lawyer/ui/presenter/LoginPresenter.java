package com.hhly.lawyer.ui.presenter;

import com.hhly.lawyer.R;
import com.hhly.lawyer.data.entity.HttpResult;
import com.hhly.lawyer.data.exception.DefaultErrorBundle;
import com.hhly.lawyer.data.exception.ErrorBundle;
import com.hhly.lawyer.data.exception.ErrorMessageFactory;
import com.hhly.lawyer.di.scope.PerActivity;
import com.hhly.lawyer.interactor.DefaultSubscriber;
import com.hhly.lawyer.ui.ContractExpands;
import com.hhly.lawyer.ui.base.BasePresenter;
import com.hhly.lawyer.ui.contract.LoginView;
import com.hhly.lawyer.ui.view.MainActivity;

import javax.inject.Inject;

@PerActivity
public class LoginPresenter extends BasePresenter<LoginView> implements ContractExpands.LoginContract {

    @Inject
    public LoginPresenter() {
    }

    private void navigateToHome() {
        this.getMvpView().navigateToHome();
    }

    public void showViewLoading() {
        this.getMvpView().showLoading();
    }

    public void hideViewLoading() {
        this.getMvpView().hideLoading();
    }

    public void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.getMvpView().context(), errorBundle.getException());

        if (errorMessage.equals(getMvpView().context().getString(R.string.username_error))) {
            this.getMvpView().setUsernameError();
        } else if (errorMessage.equals(getMvpView().context().getString(R.string.password_error))) {
            this.getMvpView().setPasswordError();
        } else {
            this.getMvpView().showError(errorMessage);
        }
    }

    @Override
    public void validateCredentials(String username, String password) {
        this.showViewLoading();
        compositeSubscription.add(((MainActivity) getMvpView().context()).getApiComponent().dataStore().postLogin(username, password)
                .subscribe(new MySubscriber()));
    }

    public final class MySubscriber extends DefaultSubscriber<HttpResult> {
        @Override
        public void onCompleted() {
            if (compositeSubscription != null) compositeSubscription.remove(this);
            LoginPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            LoginPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(HttpResult wrapper) {
            LoginPresenter.this.navigateToHome();
        }
    }
}
