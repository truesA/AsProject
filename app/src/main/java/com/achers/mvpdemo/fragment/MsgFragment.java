package com.achers.mvpdemo.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.achers.mvpdemo.R;
import com.achers.mvpdemo.base.BaseFragment;
import com.achers.mvpdemo.view.NewCreditSesameView;

/**
 * Created by Achers on 2017/11/22.
 */

public class MsgFragment extends BaseFragment {

//    private final int[] mColors = new int[]{
//            0xFFFF80AB,
//            0xFFFF4081,
//            0xFFFF5177,
//            0xFF00F260
//    };


//    private EditText editText;
//    private Button button;
//    private NewCreditSesameView zhima;
//    private LinearLayout linearLayout;



    @Override
    protected void initView() {

    }

    @Override
    protected View setRootView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.msg_fg, container, false);
        return view;
    }

    @Override
    public void initData() {

    }

//        editText= view.findViewById(R.id.ed1);
//        button=view.findViewById(R.id.b1);
//        zhima=view.findViewById(R.id.zhima);
//        linearLayout=view.findViewById(R.id.layout1);
//        linearLayout.setBackgroundColor(mColors[0]);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TextUtils.isEmpty(editText.getText().toString())){
//                    Toast.makeText(getActivity(),"请输入值",Toast.LENGTH_SHORT).show();
//                    return ;
//                }
//
//
//               int zhi = Integer.parseInt(editText.getText().toString());
//                zhima.setSesameValues(zhi);
//                startColorChangeAnim();
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        Log.e("ACtiviyt","Activity");
////        getActivity().getWindow().setSoftInputMode(
////                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
////                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//    }
//
//    public void startColorChangeAnim()
//    {
//
//        ObjectAnimator animator = ObjectAnimator.ofInt(linearLayout, "backgroundColor", mColors);
//        animator.setDuration(3000);
//        animator.setEvaluator(new ArgbEvaluator());
//
//        animator.start();
//    }

}
