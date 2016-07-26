package com.hhly.lawyer.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hhly.lawyer.R;
import com.hhly.lawyer.adapter.BaseRecyclerViewHolder;
import com.hhly.lawyer.adapter.HomeAdapter;
import com.hhly.lawyer.adapter.decration.BorderDividerItemDecration;
import com.hhly.lawyer.data.entity.HttpResult;
import com.hhly.lawyer.di.components.MainComponent;
import com.hhly.lawyer.ui.base.BaseFragment;
import com.hhly.lawyer.ui.contract.HomeView;
import com.hhly.lawyer.ui.presenter.HomePresenter;
import com.hhly.lawyer.widget.WrapSwipeRefreshLayout;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import rx.schedulers.Schedulers;

import static com.hhly.lawyer.util.Preconditions.checkNotNull;

public class HomeFragment extends BaseFragment implements HomeView, BaseRecyclerViewHolder.OnItemClickListener,
        BaseRecyclerViewHolder.OnItemLongClickListener {

    private static final String FRAGMENT_SAVED_STATE_KEY = HomeFragment.class.getSimpleName();
    /**
     * 屏幕旋转,或者其他事件,导致fragment重走生命周期方法,那么recycler的decration将会变形,要重新设置 BorderDividerItemDecration;
     */
    private boolean resestTheLifeCycle;
    private boolean loadingMoreData;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    WrapSwipeRefreshLayout swipeRefreshLayout;

    MainComponent mainComponent;
    @Inject
    HomePresenter presenter;
    private HomeAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private BorderDividerItemDecration dataDecration;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        presenter.attachView(this);
        this.setAdapter();
        this.setSwipeRefreshLayout();
    }

    @Override
    protected void initListeners() {
        checkNotNull(swipeRefreshLayout, "swipeRefreshLayout == null");

        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1);
        swipeRefreshLayout.setOnRefreshListener(() -> loadUserList());

        // footerView error callback
        ((MainActivity) context()).getAppComponent().rxBus().toObserverable().observeOn(Schedulers.io()).subscribe(o -> {
            if (null == o) loadUserList();
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean moveToDown = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (loadingMoreData) return;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (moveToDown && manager.findLastCompletelyVisibleItemPosition() > recyclerView.getChildCount() &&
                                manager.findLastCompletelyVisibleItemPosition() == (manager.getItemCount() - 1)) {
                            loadingMoreData = true;
                            loadUserList();
                        } else if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {

                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                this.moveToDown = dy > 0;
                // 弹出的ContextMenu 跟随之前的窗体滚动及消失
                //                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
                //				FeedContextMenuManager.getInstance().hideContextMenu();
            }
        });
    }

    @Override
    protected void initData() {
        loadUserList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    protected void initInjector() {
        mainComponent = getComponent(MainComponent.class);
        mainComponent.inject(this);
    }

    @Override
    protected void mOnViewStateRestored(Bundle savedInstanceState) {
        savedInstanceState.getSerializable(FRAGMENT_SAVED_STATE_KEY);
        resestTheLifeCycle = true;
    }

    @Override
    protected void mOnSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(FRAGMENT_SAVED_STATE_KEY, null);
    }

    @Override
    public void showLoading() {
        this.stopRefreshing();
    }

    @Override
    public void hideLoading() {
        this.stopRefreshing();
    }

    @Override
    public void showError(String message) {
        adapter.setFooterViewErrorVisible();
        adapter.setFooterViewLoadingInvisible();
        Snackbar snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setBackgroundColor(getResources().getColor(R.color.background_layout));
        ((TextView) snackbarLayout.findViewById(R.id.snackbar_text)).setTextColor(
                getResources().getColor(R.color.design_black_text));
        snackbar.setAction("WELL", v -> {
            snackbar.dismiss();
            stopRefreshing();
            hideLoading();
        }).show();
        recyclerView.smoothScrollToPosition(adapter.getItemCount() + 1);
    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onItemClick(View convertView, int position) {

    }

    @Override
    public void onItemLongClick(View convertView, int position) {

    }

    @Override
    public void renderUserList(HttpResult wrapper) {
        this.feedAdapter(wrapper);
    }

    private void checkTheLifeCycleIsChanging(boolean resestTheLifeCycle) {
        if (resestTheLifeCycle) {
            this.resestTheLifeCycle = false;
            this.clearDecoration();
            recyclerView.setLayoutManager(this.linearLayoutManager);
            recyclerView.addItemDecoration(this.dataDecration);
        }
    }

    private void clearDecoration() {
        this.recyclerView.removeItemDecoration(this.dataDecration);
    }

    private void feedAdapter(/*Collection<UserModel> usersCollection*/HttpResult wrapper) {
        this.stopRefreshing();
        if (wrapper == null) return;
        checkNotNull(adapter, "adapter == null");
//        this.usersLists = (ArrayList<UserModel>)usersCollection;
        ((MainActivity) getActivity()).getAppComponent().rxBus().send(wrapper.data);

        if (loadingMoreData) {
            loadingMoreData = false;
            adapter.addAll(Arrays.asList(new String("1"), new String("1"), new String("1"), new String("1")));
            adapter.dismissFooterViewLoading();
        } else {
            this.checkTheLifeCycleIsChanging(resestTheLifeCycle);
            adapter.setList(Arrays.asList(new String("1"), new String("1"), new String("1"), new String("1"), new String("1"), new String("1"), new String("1"), new String("1"), new String("1"), new String("1")));
            adapter.notifyDataSetChanged();
        }
    }

    private void stopRefreshing() {
        checkNotNull(swipeRefreshLayout, "swipeRefreshLayout == null");
        if (adapter != null) adapter.setFooterViewLoadingInvisible();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void loadUserList() {
        presenter.initialize();
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new HomeAdapter(getActivity(), recyclerView);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.setOnItemLongClickListener(this);
        }
    }

    private void setSwipeRefreshLayout() {
        // 为了保证上拉加载数据时，加载到的数据整体能全部显示在屏幕内，则必须使用自定义Decration，如果不适用则有一小部分会隐藏在屏幕下面
        dataDecration = new BorderDividerItemDecration(getResources().getDimensionPixelOffset(R.dimen
                .data_border_divider_height));
        recyclerView.addItemDecoration(dataDecration);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
