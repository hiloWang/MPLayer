package com.hhly.lawyer.ui.base;

import com.hhly.lawyer.ui.MvpPresenter;
import com.hhly.lawyer.ui.MvpView;

import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

	private T mvpView;
	public CompositeSubscription compositeSubscription;

	@Override public void attachView(T mvpView) {
		this.mvpView = mvpView;
		this.compositeSubscription = new CompositeSubscription();
	}

	@Override public void detachView() {
		this.mvpView = null;
		if (!compositeSubscription.isUnsubscribed()) {
			compositeSubscription.unsubscribe();
			compositeSubscription = null;
		}
	}

	public boolean isViewAttached() {
		return mvpView != null;
	}

	public void checkViewAttached() {
		if (!isViewAttached()) throw new MvpViewNotAttachedException();
	}

	public T getMvpView() {
		return mvpView;
	}

	public static class MvpViewNotAttachedException extends RuntimeException {
		public MvpViewNotAttachedException() {
			super("Please call MvpPresenter.attachView(MvpView) before" + " requesting data to the " +
										"MvpPresenter");
		}
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

	}
}
