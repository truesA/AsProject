package com.achers.mvpdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.achers.mvpdemo.R;
import com.achers.mvpdemo.base.BaseFragment;

/**
 * Created by Achers on 2017/11/22.
 */

public class MineFragment extends BaseFragment {

    @Override
    protected View setRootView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.mine_fg, container,false);


        return view;
    }

    @Override
    protected void initView() {

    }



    @Override
    public void initData() {

    }

}
