package com.hhly.lawyer.ui.contract;

import com.hhly.lawyer.data.entity.Wrapper;
import com.hhly.lawyer.ui.LoadingDataView;

public interface HomeView extends LoadingDataView {

    void renderUserList(Wrapper wrapper);
}
