package com.hhly.lawyer.data.exception;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.facebook.stetho.inspector.MismatchedResponseException;
import com.hhly.lawyer.data.R;
import com.hhly.lawyer.data.util.Logger;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    private static final String TAG = "ErrorMessageFactory";

    private ErrorMessageFactory() {
        //empty
    }

    public static String create(Context context, Exception exception) {
        if (!TextUtils.isEmpty(exception.getMessage())) {
            Logger.e(TAG, exception.getMessage());
        }

        String message = exception.getMessage();

        if (exception instanceof NetworkErrorException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof Resources.NotFoundException) {
            message = context.getString(R.string.exception_message_not_found);
        } else if (exception instanceof MismatchedResponseException) {
            message = exception.getMessage();
        } else if (exception instanceof HttpException) {
                message = exception.getMessage();
        }
        return message;
    }
}