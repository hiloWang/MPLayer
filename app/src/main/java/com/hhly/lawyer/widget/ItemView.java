package com.hhly.lawyer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hhly.lawyer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemView extends FrameLayout {

    @BindView(R.id.imageIcon)
    ImageView icon;
    @BindView(R.id.contentHintText)
    EditText contentHintText;
    @BindView(R.id.textInputLayout)
    TextInputLayout textInputLayout;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_item_icon, this);
        ButterKnife.bind(this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LawyerItemView);
        for (int i = 0; i < ta.length(); i++) {
            int attrId = ta.getIndex(i);
            switch (attrId) {
                case R.styleable.LawyerItemView_lawyer_left_image_icon:
                    int resourceId = ta.getResourceId(attrId, 0);
                    if (resourceId != 0) icon.setImageResource(resourceId);
                    break;
                case R.styleable.LawyerItemView_lawyer_content_hint:
                    String text = ta.getString(attrId);
                    if (!TextUtils.isEmpty(text)) {
                        textInputLayout.setHint(text);
                        if (text.equals(context.getString(R.string.login_input_phone_num))) {
                            contentHintText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        } else if (text.equals(context.getString(R.string.login_verify_code))) {
                            contentHintText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        } else if (text.equals(context.getString(R.string.login_input_password))
                                || text.equals(context.getString(R.string.login_input_password_again))) {
                            contentHintText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }
                    }
                    break;
            }
        }
        ta.recycle();
    }

    public String getText() {
        return contentHintText.getText().toString();
    }
}
