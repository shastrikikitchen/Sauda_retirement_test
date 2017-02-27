package com.traveller.enthusiastic.goldwise.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traveller.enthusiastic.goldwise.R;
import com.traveller.enthusiastic.goldwise.helper.BaseFragment;

/**
 * Created by sauda on 25/02/17.
 */

public class FundsFragment  extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragments_funds, container, false);
    }
}
