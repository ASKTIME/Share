package com.example.share;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.share.Adapter.SectionPagerAdapter;
import com.example.share.Fragment.FragmentChat;
import com.example.share.Fragment.FragmentHome;
import com.example.share.Fragment.FragmentMine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewpager;
    private BottomNavigationBar mBottomNavigationBar;

    private List<Fragment>  fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewpager = findViewById(R.id.viewpager);
        mBottomNavigationBar = findViewById(R.id.bottom);
        initView();
    }

    private void initView() {
        initViewPager();
        initBottomNavigationBar();
    }

    private void initBottomNavigationBar() {

        //配置底部导航栏
        mBottomNavigationBar.setTabSelectedListener(this);
        mBottomNavigationBar.clearAll();
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);  //自适应大小
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        mBottomNavigationBar.setBarBackgroundColor(R.color.white)
                .setActiveColor(R.color.blue)
                .setInActiveColor(R.color.black);

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.homepage_fill,"首页").setInactiveIconResource(R.mipmap.homepage))
                .addItem(new BottomNavigationItem(R.mipmap.mobilephone_fill,"论坛").setInactiveIconResource(R.mipmap.mobilephone))
                .addItem(new BottomNavigationItem(R.mipmap.mine_fill,"我的").setInactiveIconResource(R.mipmap.mine))
                .setFirstSelectedPosition(0)
                .initialise();

    }

    private void initViewPager() {
        //配置ViewPager
        mViewpager.setOffscreenPageLimit(3);

        fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentHome());
        fragments.add(new FragmentChat());
        fragments.add(new FragmentMine());

        mViewpager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager(),fragments));
        mViewpager.addOnPageChangeListener(this);
        mViewpager.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(int position) {
        mViewpager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        mViewpager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        //选择到哪一个tab
        mBottomNavigationBar.selectTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}


























/*BmobUser user = BmobUser.getCurrentUser(User.class);
        String id = user.getObjectId();
        BmobQuery<User> myUser = new BmobQuery<User>();
        myUser.getObject(id, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null) {

                }else {
                    Toast.makeText(MainActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
