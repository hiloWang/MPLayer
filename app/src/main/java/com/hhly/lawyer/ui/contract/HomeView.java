package com.hhly.lawyer.ui.contract;

import com.hhly.lawyer.data.entity.Wrapper;
import com.hhly.lawyer.ui.LoadingMvpView;

public interface HomeView extends LoadingMvpView {

    void renderUserList(Wrapper wrapper);
}
