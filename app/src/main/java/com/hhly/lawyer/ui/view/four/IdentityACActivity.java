package com.hhly.lawyer.ui.view.four;

import android.os.Bundle;

import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.base.BaseToolbarActivity;

public class IdentityACActivity extends BaseToolbarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.setToolbarTitle(getString(R.string.identity_ac_title));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }
}
