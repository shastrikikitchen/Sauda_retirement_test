package com.traveller.enthusiastic.goldwise.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.traveller.enthusiastic.goldwise.R;

/**
 * Created by sauda on 25/02/17.
 */

public abstract class  BaseFragment extends Fragment {
    protected ProgressBar progressBar;
    private boolean isHomeUpEnabled;
    Toast mToast;
    protected void setTitle(int stringResId) {
        setTitle(getResources().getString(stringResId));
    }
    public void setTitle(String title) {
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);

        }
    }
    protected void setSubTitle(String subTitle) {
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setSubtitle(subTitle);
        }
    }
    protected void removeSubTitle() {
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setSubtitle(null);
        }
    }
    protected void showShortToast(String message) {
        Context context = (AppCompatActivity) getActivity();

        if(context!=null && mToast==null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }

        if(!TextUtils.isEmpty(message)) {
            if(mToast!=null && !mToast.getView().isShown()) {
                mToast.setText(message);
                mToast.show();
            }
        }
    }

    protected ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }



}
