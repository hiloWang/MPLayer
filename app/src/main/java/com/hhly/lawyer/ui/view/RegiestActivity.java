package com.hhly.lawyer.ui.view;

import android.os.Bundle;
import android.widget.Button;

import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.base.BaseToolbarActivity;
import com.hhly.lawyer.widget.IconItemView;

import butterknife.BindView;

public class RegiestActivity extends BaseToolbarActivity {

    @BindView(R.id.phoneNum)
    IconItemView phoneNum;
    @BindView(R.id.verifyCode)
    IconItemView verifyCode;
    @BindView(R.id.password)
    IconItemView password;
    @BindView(R.id.confirm_password)
    IconItemView confirmPassword;
    @BindView(R.id.confirm)
    Button confirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regiest;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }
}
