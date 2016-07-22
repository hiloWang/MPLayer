package com.hhly.lawyer.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.base.BaseFragment;
import com.hhly.lawyer.widget.ArrowItemView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class PagerFragment1 extends BaseFragment {

    public static final String ARGS_PAGE = "args_page";
    @BindView(R.id.sivTest)
    ArrowItemView sivTest;
    private int page;

    @BindView(R.id.textView)
    TextView textView;

    public static PagerFragment1 newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        PagerFragment1 fragment = new PagerFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pager1;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        page = getArguments().getInt(ARGS_PAGE);
        textView.setText("第" + page + "页");
    }

    @Override
    protected void initListeners() {
        RxView.clicks(textView).throttleFirst(1, TimeUnit.SECONDS).subscribe(aVoid -> {
            startActivity(new Intent(getActivity(), RegiestActivity.class));
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void mOnViewStateRestored(Bundle savedInstanceState) {

    }

    @Override
    protected void mOnSaveInstanceState(Bundle savedInstanceState) {

    }
}
