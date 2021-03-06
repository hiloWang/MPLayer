package com.hhly.lawyer.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hhly.lawyer.R;
import com.hhly.lawyer.ui.view.MainActivity;
import com.hhly.lawyer.util.DateUtils;
import com.hhly.lawyer.util.Utils;
import com.hhly.lawyer.widget.footerview.MaterialFooterView;
import com.jakewharton.rxbinding.view.RxView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.schedulers.Schedulers;

import static com.hhly.lawyer.util.Preconditions.checkNotNull;

public class HomeAdapter extends BaseRecyclerViewAdapter {

    private Context context;
    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_FOOTERVIEW = 2;

    private final int[] drawableIcons = new int[] {R.drawable.ic_menu_home,R.drawable.ic_menu_home,R.drawable.ic_menu_home,R.drawable
            .ic_menu_home,R.drawable.ic_menu_home};

    private RecyclerView recyclerView;
    private FrameLayout footerViewContainer;
    private CardView errorFooterView;
    private int position;

    public HomeAdapter(Context context, RecyclerView recyclerView) {
        super();
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override public int[] getItemLayouts() {
        return new int[] {R.layout.item_home_adapter,R.layout.view_rv_footer};
    }

    @Override public void onBindRecycleViewHolder(BaseRecyclerViewHolder viewHolder, int position) {
        int itemViewType = getRecycleViewItemType(position);
        switch (itemViewType) {
            case VIEW_TYPE_DEFAULT:
                bindDefaultView(viewHolder,position);
                break;
            case VIEW_TYPE_FOOTERVIEW:
                bindFooterView(viewHolder,position);
                break;
        }
    }

    @Override public int getRecycleViewItemType(int position) {
        // 保证数据少于一个屏幕时, 不显示上拉加载icon
        if (position != 0 && position > recyclerView.getChildCount() && position == getListSize()) return VIEW_TYPE_FOOTERVIEW;
        return VIEW_TYPE_DEFAULT;
    }

    @SuppressLint("SetTextI18n") private void bindDefaultView(BaseRecyclerViewHolder viewHolder,int position) {
        String data = getItemByPosition(position);
        checkNotNull(data,"data == null");
        CardView container = viewHolder.findViewById(R.id.container);
        TextView mDailyTitleTv = viewHolder.findViewById(R.id.daily_title_tv);
        TextView mDailyDateTv = viewHolder.findViewById(R.id.daily_date_tv);
        ImageView mDailyIv = viewHolder.findViewById(R.id.daily_iv);

        container.setVisibility(View.VISIBLE);
        mDailyTitleTv.setText("华海乐盈" + position);
        mDailyDateTv.setText(DateUtils.date2yyyyMMdd(new Date()));

        mDailyIv.setScaleX(0.f);
        mDailyIv.setScaleY(0.f);

		/*Log.e("HILO","当前内存： " + Glide.getPhotoCacheDir(context).getAbsolutePath() + "::" + Glide.getPhotoCacheDir(context)
																																														.getFreeSpace());*/
        Glide.with(context)
                .load(drawableIcons[(int)(Math.random() * 5)])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_launcher)
                .into(mDailyIv);

        mDailyIv.animate().scaleX(1.f).scaleY(1.f).setInterpolator(new OvershootInterpolator()).setDuration(1000);

        // ImageView onClicked event
//		RxView.clicks(mDailyIv)
//					.throttleFirst(1,TimeUnit.SECONDS)
//					.observeOn(Schedulers.newThread())
//					.map((Func1<Void, Void>)aVoid -> {
//
//						Glide.get(context).clearDiskCache();
//						/*Log.e("HILO","清理后disk： 总空间(" + Glide.getPhotoCacheDir(context).getTotalSpace() + ")" +
//										":: 可用空间（" + Glide.getPhotoCacheDir(context).getUsableSpace() + ")");*/
//						return null;
//					})
//					.observeOn(AndroidSchedulers.mainThread())
//					.subscribe(aVoid -> {
//
//						Glide.get(context).clearMemory();
//						/*Log.e("HILO",
//									"清理后内存： " + Glide.getPhotoCacheDir(context).getAbsolutePath() + "::" +
//													Glide.getPhotoCacheDir(context).getFreeSpace());*/
//					});
    }

    private void bindFooterView(BaseRecyclerViewHolder viewHolder,int position) {
        this.position = position;
        errorFooterView = viewHolder.findViewById(R.id.errorFooterView);
        footerViewContainer = viewHolder.findViewById(R.id.footerViewContainer);
        MaterialFooterView footerViewIcon = viewHolder.findViewById(R.id.footerViewIcon);

        footerViewContainer.setTranslationY(0f);
        startFooterViewLoading(footerViewIcon);
        // error: Only the original thread that created a view hierarchy can touch its views.
        // 注意,这里线程转换成子线程,feedAdapter方法走notify时一定要先请求一次api(因为做测试时,没有请求api操作,所以报错了,正常流程的话不会出现此错误),将线程转换成主线程后,在notify
        RxView.clicks(errorFooterView).throttleFirst(1,TimeUnit.SECONDS).observeOn(Schedulers.io()).subscribe(aVoid -> {
            ((MainActivity)context).getAppComponent().rxBus().send(null);
        });

        if (Utils.isNetworkConnected(context)) errorFooterView.setVisibility(View.GONE);
    }

    private void startFooterViewLoading(View loadingView) {
        Animation loadingAnimation = AnimationUtils.loadAnimation(context,R.anim.rotate_view_footer);
        // 保持匀速旋转的interpolator
        LinearInterpolator interpolator = new LinearInterpolator();
        loadingAnimation.setInterpolator(interpolator);
        loadingView.startAnimation(loadingAnimation);
    }

    public void dismissFooterViewLoading() {
        if (footerViewContainer != null) {
            this.setFooterViewErrorGone();
            this.setFooterViewVisible();
            footerViewContainer.animate().translationY(400f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    notifyItemChanged(getItemCount());
                }
            });
        }
    }

    public void setFooterViewVisible() {
        if (footerViewContainer != null) {
            if (footerViewContainer.getVisibility() == View.INVISIBLE) footerViewContainer.setVisibility(View.VISIBLE);
        }
    }

    public void setFooterViewLoadingInvisible() {
        if (footerViewContainer != null) {
            if (errorFooterView.getVisibility() == View.VISIBLE) footerViewContainer.setVisibility(View.INVISIBLE);
        }
    }

    public void setFooterViewErrorVisible() {
        if (errorFooterView != null) {
            if (position != 0) {
                if (errorFooterView.getVisibility() == View.GONE) errorFooterView.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setFooterViewErrorGone() {
        if (errorFooterView != null) {
            if (errorFooterView.getVisibility() == View.VISIBLE) errorFooterView.setVisibility(View.GONE);
        }
    }
}
