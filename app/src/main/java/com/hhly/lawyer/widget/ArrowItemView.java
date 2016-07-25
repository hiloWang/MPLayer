package com.hhly.lawyer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhly.lawyer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArrowItemView extends FrameLayout {

    @BindView(R.id.imageIcon)
    ImageView mLeftIV;

    @BindView(R.id.contentHintText)
    TextView contentHintText;

    public ArrowItemView(Context context) {
        this(context , null);
    }

    public ArrowItemView(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public ArrowItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_item_arrow, this);
        ButterKnife.bind(this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ArrowItemView);
        for (int i = 0 ; i < ta.length() ; i++){
            int attrId = ta.getIndex(i);
            switch (attrId){
                case R.styleable.ArrowItemView_arrow_left_image_icon:
                    int resourceId = ta.getResourceId(attrId, 0);
                    if (resourceId != 0){
                        mLeftIV.setImageResource(resourceId);
                    }
                    break;
                case R.styleable.ArrowItemView_arrow_content_text:
                    contentHintText.setHint(ta.getString(attrId));
                    break;
            }
        }
        ta.recycle();
    }
}
