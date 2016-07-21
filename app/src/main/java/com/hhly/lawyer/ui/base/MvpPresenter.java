package com.hhly.lawyer.ui.base;

import com.hhly.lawyer.ui.MvpView;

/**
 * Description：MvpPresenter
 * <p>
 * Base class that implements the MvpPresenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 * <p>
 */
public interface MvpPresenter<V extends MvpView> {

	void attachView(V mvpView);

	void detachView();

	/**
	 * Method that control the lifecycle of the view. It should be called in the view's
	 * (Activity or Fragment) onResume() method.
	 */
	void resume();

	/**
	 * Method that control the lifecycle of the view. It should be called in the view's
	 * (Activity or Fragment) onPause() method.
	 */
	void pause();

	/**
	 * Method that control the lifecycle of the view. It should be called in the view's
	 * (Activity or Fragment) onDestroy() method.
	 */
	void destroy();

	void initialize();
}
