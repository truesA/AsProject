package com.achers.mvpdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.achers.mvpdemo.R;
import com.achers.mvpdemo.activity.BankCardActivity;
import com.achers.mvpdemo.base.BaseFragment;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

/**
 * Created by Achers on 2017/11/22.
 */

public class ServiceFragment extends BaseFragment {

    @BindView(R.id.mine_bank)
    public LinearLayout mine_bank;

    @Override
    protected View setRootView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.service_fg, container,false);


        return view;
    }

    @Override
    public void initData() {

    }


    @Override
    protected void initView() {

        mine_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent =  new Intent(getActivity(), BankCardActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        //3、创建线程池对象，调用submit()方法执行MyCallable任务，并返回Future对象
//        ExecutorService pool = Executors.newSingleThreadExecutor();
//        Future<Integer> f1 = pool.submit(new MyCallable());
////4、调用Future对象的get()方法获取call()方法执行完后的值
//        try {
//            Log.d(TAG, "sum=" + f1.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        //关闭线程池
//        pool.shutdown();
//    }
//
//
//
//    //1、自定义一个类MyCallable实现Callable接口，并重写call()方法
//    public class MyCallable implements Callable<Integer> {
//
//        @Override
//        public Integer call() throws Exception {
//            //2、将要执行的代码写在call()方法中
//            int sum = 0;
//            for (int i = 0; i <= 100; i++) {
//                sum += i;
//            }
//            return sum;
//        }
//    }
}
