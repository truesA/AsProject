package com.achers.mvpdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.achers.mvpdemo.R;
import com.achers.mvpdemo.base.BaseActivity;

public class BankCardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbarTitle().setText("银行卡");
        showIvRight();
    }

    @Override
    protected int getlayoutview() {
        return R.layout.activity_bank_card;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isUseToolsBar() {
        return true;
    }


}
