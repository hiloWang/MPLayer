package com.hhly.lawyer.ui.contract;

import com.hhly.lawyer.data.entity.HttpResult;
import com.hhly.lawyer.ui.LoadingMvpView;

public interface HomeView extends LoadingMvpView {

    void renderUserList(HttpResult httpResult);
}
