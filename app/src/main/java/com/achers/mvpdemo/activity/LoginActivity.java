package com.achers.mvpdemo.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.achers.mvpdemo.App;
import com.achers.mvpdemo.MainActivity;
import com.achers.mvpdemo.R;
import com.achers.mvpdemo.base.BaseActivity;
import com.achers.mvpdemo.interfaces.GankService;
import com.achers.mvpdemo.util.UIUtils;
import com.joker.annotation.PermissionsCustomRationale;
import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.api.Permissions4M;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

//    @BindView(R.id.login_tool)
//    public Toolbar login_tool;
//    @BindView(R.id.layout_username)
//    public TextInputLayout nametextInputLayout;
//    @BindView(R.id.layout_userpassword)
//    public TextInputLayout passwordtextInputLayout;
    @BindView(R.id.et_name)
    public EditText nameEt;
    @BindView(R.id.et_password)
    public EditText passwordEt;
    @BindView(R.id.bt_login)
    public Button login;
    @BindView(R.id.et_close)
    public ImageView et_close;
    @BindView(R.id.et_password_type)
    public ImageView et_password_type;
    @BindView(R.id.login_forget)
    public TextView login_forget;
    @BindView(R.id.login_register)
    public TextView login_register;
    @BindView(R.id.login_qq)
    public ImageView login_qq;
    @BindView(R.id.login_weixin)
    public ImageView login_weixin;
    @BindView(R.id.login_sina)
    public ImageView login_sina;

    private final int charMaxNum = 20;
    private final int charMaxNumpass = 16;
    private Boolean showPassword = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_login);
       // ButterKnife.bind(this);

        getToolbarTitle().setText("登录");
      //  setSupportActionBar(login_tool);
//        login_tool.setNavigationIcon(R.drawable.back_icon);
     //   showIvRight();
        //String register="还没有帐号,";
       // String register=String.format("状态 ：<font color=\"#1296db\">%s");
     //   login_register.setText("还没有帐号,"+Html.fromHtml(register));
        String content = "还没有帐号,<font color=\"#1296db\">去注册</font>";
        if (Build.VERSION.SDK_INT >= 24)
            login_register.setText(Html.fromHtml(content,Html.FROM_HTML_MODE_COMPACT));
        else
            login_register.setText(Html.fromHtml(content));


        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.length()>charMaxNum)
                  //  nametextInputLayout.setError("超过最大输入限制");
                    UIUtils.showToast("超过最大输入限制");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    et_close.setVisibility(View.VISIBLE);
                }else{
                    et_close.setVisibility(View.GONE);
                }

                if (s.length()>charMaxNumpass)
                    UIUtils.showToast("密码超过最大输入限制");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }




//    protected void initToolBar() {
//        getToolbarTitle().setText("登录");
//        getSubTitle().setText("右边标题");
//        Toolbar toolbar = getToolbar();
//        toolbar.setLogo(R.mipmap.ic_launcher);
//    }

    @OnClick({R.id.et_close,R.id.et_password_type, R.id.login_forget,R.id.login_register,R.id.login_qq,R.id.login_weixin,R.id.login_sina})
    public void onViewClicked(View view){
        Intent intent =new Intent();

        switch (view.getId()){
            case R.id.et_close:
                passwordEt.setText("");
                break;

            case R.id.et_password_type:


                if (showPassword) {// 显示密码
                    et_password_type.setImageDrawable(getResources().getDrawable(R.drawable.eye_open));
                    passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordEt.setSelection(passwordEt.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    et_password_type.setImageDrawable(getResources().getDrawable(R.drawable.eye_close));
                    passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordEt.setSelection(passwordEt.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.login_forget:
               intent.putExtra("intentflag",true);
               intent.setClass(this,RegisteAndForgetPassActivity.class);
               startActivity(intent);
                break;
            case R.id.login_register:
                intent.putExtra("intentflag",false);
                intent.setClass(this,RegisteAndForgetPassActivity.class);
                startActivity(intent);
                break;
            case R.id.login_qq:
                UIUtils.showToast("QQ登录");
                App.getRetrofit("https://872152c8.ngrok.io")
                        .create(GankService.class)
                        .getGanHuo("1")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String ganHuo) {
                                System.out.print(ganHuo);
                                Log.e("MSG",ganHuo);
                            }

                            @Override
                            public void onError(Throwable e) {
                                // noWIFILayout.setVisibility(View.VISIBLE);
                                System.out.print(e.toString());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.login_weixin:
                UIUtils.showToast("微信登录");
                break;
            case R.id.login_sina:
                UIUtils.showToast("新浪登录");
                break;

                default:
                    break;
        }
    }


    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    protected int getlayoutview() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    /**
     * 请求接口
     */



    private static final int AUDIO_CODE = 6;

    @OnClick(R.id.bt_login)
    public void goLogin(){
        hideKeyboard();
       String name= nameEt.getText().toString();
       String password =passwordEt.getText().toString();


       startActivity(new Intent(this,MainActivity.class));
//        Permissions4M.get(LoginActivity.this)
//                // 是否强制弹出权限申请对话框，建议设置为 true，默认为 true
//                .requestForce(true)
//                // 是否支持 5.0 权限申请，默认为 false
//                // .requestUnderM(false)
//                // 权限，单权限申请仅只能填入一个
//                .requestPermissions(Manifest.permission.RECORD_AUDIO)
//                // 权限码
//                .requestCodes(AUDIO_CODE)
//                // 如果需要使用 @PermissionNonRationale 注解的话，建议添加如下一行
//                // 返回的 intent 是跳转至**系统设置页面**
//                 .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
//                // 返回的 intent 是跳转至**手机管家页面**
//                // .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
//                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(LoginActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionsGranted(AUDIO_CODE)
    public void smsAndAudioGranted() {
        UIUtils.showToast("录音权限申请成功 in activity with annotation");
    }

    @PermissionsDenied(AUDIO_CODE)
    public void smsAndAudioDenied() {
        UIUtils.showToast("录音权限申请失败 in activity with annotation");
    }

    @PermissionsCustomRationale(AUDIO_CODE)
    public void smsAndAudioCustomRationale() {
        new AlertDialog.Builder(this)
                .setMessage("录音权限申请：\n我们需要您开启录音权限(in activity with annotation)")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Permissions4M.get(LoginActivity.this)
                                .requestOnRationale()
                                .requestPermissions(Manifest.permission.RECORD_AUDIO)
                                .requestCodes(AUDIO_CODE)
                                .request();
                    }
                })
                .show();
    }




}
