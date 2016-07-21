package com.hhly.lawyer.ui;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadingMvpView extends MvpView {
	/**
	 * Show a view with a progress bar indicating a loading process.
	 */
	void showLoading();

	/**
	 * Hide a loading view.
	 */
	void hideLoading();

	/**
	 * Show an error message
	 *
	 * @param message A string representing an error.
	 */
	void showError(String message);

}
