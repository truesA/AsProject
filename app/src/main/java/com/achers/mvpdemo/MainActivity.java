package com.achers.mvpdemo;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.achers.mvpdemo.base.BaseActivity;
import com.achers.mvpdemo.fragment.DataFragment;
import com.achers.mvpdemo.fragment.MineFragment;
import com.achers.mvpdemo.fragment.MsgFragment;
import com.achers.mvpdemo.fragment.MyViewPagerAdapter;
import com.achers.mvpdemo.fragment.PersonFragment;
import com.achers.mvpdemo.fragment.ServiceFragment;
import com.achers.mvpdemo.view.NoSlidingViewPaper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

//    @BindView(R.id.toolbar)
//    public Toolbar toolbar;
    @BindView(R.id.navigation_view)
    public BottomNavigationView navigation_view;
    @BindView(R.id.view_page)
    public NoSlidingViewPaper viewPaper;

    public android.support.v4.app.FragmentManager fragmentManager;
    public DataFragment dataFragment;
    public MsgFragment msgFragment;
    public ServiceFragment serviceFragment;
    public PersonFragment personFragment;
    public MineFragment mineFragment;

    public List<Fragment> fragments =new ArrayList<>();

    public static final String POSITION = "position";
    public static final int FRAGMENT_ONE=0;
    public static final int FRAGMENT_TWO=1;
    public static final int FRAGMENT_THREE=2;
    public static final int FRAGMENT_FOUR=3;
    public static final int FRAGMENT_FIVE=4;

    public static final int CALENDAR_CODE = 7;
    public static final int SENSORS_CODE = 8;
    public static final int LOCATION_CODE = 9;

    private int position;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

      //  toolbar.setLogo(R.mipmap.ic_launcher);
//        toolbar.setTitle("微动力信用");
//
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
//
//        setSupportActionBar(toolbar);


    }

    @Override
    protected boolean isUseToolsBar() {
        return false;
    }

    @Override
    protected int getlayoutview() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

//        viewPaper=findViewById(R.id.view_page);
//
//        navigation_view=findViewById(R.id.navigation_view);
        initFragment();

        viewPaper
                .setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), fragments));
        //fragmentManager =getSupportFragmentManager();

        //disableShiftMode(navigation_view);
        navigation_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_nav_ui:
                        //    mNavTv.setText(item.getTitle());
                        viewPaper.setCurrentItem(0);
                        break;
                    case R.id.bottom_nav_data:
                        //    mNavTv.setText(item.getTitle());
                        viewPaper.setCurrentItem(1);
                        break;
                    case R.id.bottom_nav_service:
                        //    mNavTv.setText(item.getTitle());
                        viewPaper.setCurrentItem(2);
                        break;
//                case R.id.bottom_nav_net:
//             //   mNavTv.setText(item.getTitle());
//                    viewPaper.setCurrentItem(3);
//                break;
//                case R.id.bottom_nav_media:
//             //   mNavTv.setText(item.getTitle());
//                    viewPaper.setCurrentItem(4);
//               break;
                }
                return true;


            }
        });
    }

    @Override
    protected void initData() {

    }

    private void initFragment() {
        dataFragment =new DataFragment();
        msgFragment =new MsgFragment();
        serviceFragment =new ServiceFragment();
//        personFragment =new PersonFragment();
//        mineFragment =new MineFragment();

        fragments.add(dataFragment);
        fragments.add(msgFragment);
        fragments.add(serviceFragment);
//        fragments.add(personFragment);
//        fragments.add(mineFragment);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);

        try {

            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");

            shiftingMode.setAccessible(true);

            shiftingMode.setBoolean(menuView, false);

            shiftingMode.setAccessible(false);



            for (int i = 0; i < menuView.getChildCount(); i++) {

                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);

                itemView.setShiftingMode(false);

                itemView.setChecked(itemView.getItemData().isChecked());

            }

        } catch (NoSuchFieldException | IllegalAccessException e) {

            e.printStackTrace();

        }

    }


}
