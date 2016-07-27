package com.hhly.lawyer.ui.presenter;

import com.hhly.lawyer.data.entity.HttpResult;
import com.hhly.lawyer.data.exception.DefaultErrorBundle;
import com.hhly.lawyer.data.exception.ErrorBundle;
import com.hhly.lawyer.data.exception.ErrorMessageFactory;
import com.hhly.lawyer.di.scope.PerActivity;
import com.hhly.lawyer.interactor.DefaultSubscriber;
import com.hhly.lawyer.ui.base.BasePresenter;
import com.hhly.lawyer.ui.contract.HomeView;

import javax.inject.Inject;

@PerActivity
public class HomePresenter extends BasePresenter<HomeView> {

    @Inject
    public HomePresenter() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void initialize() {
        showViewLoading();
//        compositeSubscription.add(((MainActivity) getMvpView().context()).getApiComponent().dataStore().getDummyData("18686812686", "4")
//                .subscribe(new MySubscriber()));
        this.getMvpView().renderUserList(null);
    }

    private void showViewLoading() {
        this.getMvpView().showLoading();
    }

    private void hideViewLoading() {
        this.getMvpView().hideLoading();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(getMvpView().context(), errorBundle.getException());
        getMvpView().showError(errorMessage);
    }


    private void showUsersInView(HttpResult httpResult) {
        this.getMvpView().renderUserList(httpResult);
    }

    private final class MySubscriber extends DefaultSubscriber<HttpResult> {
        @Override
        public void onCompleted() {
            if (compositeSubscription != null) compositeSubscription.remove(this);
            HomePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            HomePresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(HttpResult wrapper) {
            HomePresenter.this.showUsersInView(wrapper);
        }
    }
}
