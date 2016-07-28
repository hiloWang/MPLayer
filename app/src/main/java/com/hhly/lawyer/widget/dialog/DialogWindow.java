package com.hhly.lawyer.widget.dialog;

import android.os.Bundle;
import android.view.View;

import com.hhly.lawyer.R;

import butterknife.OnClick;

public class DialogWindow extends BaseDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_default;
    }

    @OnClick({R.id.btnCancel , R.id.btnConfirm})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnConfirm:
                dismiss();
                if (getParentFragment() != null && getParentFragment() instanceof Interaction){
                    Interaction i = (Interaction)getParentFragment();
                    i.onDialogConfirm();
                }else if(getActivity() != null && getActivity() instanceof Interaction){
                    Interaction i = (Interaction)getActivity();
                    i.onDialogConfirm();
                }
                break;
        }
    }

    public interface Interaction{
        void onDialogConfirm();
    }

    public static DialogWindow newInstance() {
        Bundle args = new Bundle();
        DialogWindow fragment = new DialogWindow();
        fragment.setArguments(args);
        return fragment;
    }
}
