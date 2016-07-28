package com.hhly.lawyer.ui.view.four;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.hhly.lawyer.R;
import com.hhly.lawyer.data.entity.HttpResult;
import com.hhly.lawyer.data.exception.DefaultErrorBundle;
import com.hhly.lawyer.data.exception.ErrorBundle;
import com.hhly.lawyer.data.exception.ErrorMessageFactory;
import com.hhly.lawyer.interactor.DefaultSubscriber;
import com.hhly.lawyer.ui.base.BaseToolbarActivity;
import com.hhly.lawyer.util.CountDown;
import com.hhly.lawyer.widget.ItemView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.subscriptions.CompositeSubscription;

public class ForgotYourPasswordActivity extends BaseToolbarActivity {

    @BindView(R.id.phoneNum)
    ItemView phoneNum;
    @BindView(R.id.verifyCode)
    ItemView verifyCode;
    @BindView(R.id.getVerifyCode)
    TextView getVerifyCode;
    @BindView(R.id.password)
    ItemView password;
    @BindView(R.id.confirm_password)
    ItemView confirmPassword;
    @BindView(R.id.confirm)
    Button confirm;

    private CompositeSubscription compositeSubscription;
    private CountDown countDownTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgotpwd;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    protected void initData() {
        this.setToolbarTitle(getString(R.string.forgotyourpwd));
    }

    @Override
    protected void initListeners() {
        RxView.clicks(getVerifyCode).throttleFirst(1, TimeUnit.SECONDS).subscribe(this::getVerifyCode);

        countDownTimer = CountDown.getDefault(new CountDown.CountDownCallback() {
            @Override
            public void onFinish() {
                ForgotYourPasswordActivity.this.setVerifyViewEnabled(true, getString(R.string.login_get_verify_code));
            }

            @Override
            public void onTick(long millisUntilFinished) {
                ForgotYourPasswordActivity.this.setVerifyViewEnabled(false, millisUntilFinished / CountDown.TIMEOUT_INTERVEL + "秒");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
            compositeSubscription = null;
        }
    }

    private void setVerifyViewEnabled(boolean enabled, String text) {
        getVerifyCode.setEnabled(enabled);
        getVerifyCode.setText(text);
    }

    private void getVerifyCode(Void aVoid) {
        String number = phoneNum.getText();
        if (TextUtils.isEmpty(number)) {
            this.showToast(getString(R.string.validate_phone_number));
            return;
        }
        compositeSubscription.add(getApiComponent().dataStore().getDummyData(number, "4").subscribe(new MySubscriber()));
        countDownTimer.start();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(ForgotYourPasswordActivity.this, errorBundle.getException());
        this.showToast(errorMessage);
    }

    private final class MySubscriber extends DefaultSubscriber<HttpResult> {
        @Override
        public void onCompleted() {
            if (compositeSubscription != null) compositeSubscription.remove(this);
        }

        @Override
        public void onError(Throwable e) {
            ForgotYourPasswordActivity.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            ForgotYourPasswordActivity.this.countDownTimer.cancel();
            ForgotYourPasswordActivity.this.setVerifyViewEnabled(true, getString(R.string.login_get_verify_code));
        }

        @Override
        public void onNext(HttpResult wrapper) {
            ForgotYourPasswordActivity.this.showToast("获取验证码成功");
        }
    }
}
