package com.example.share.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.share.Fragment.FragmentMyCommunity;
import com.example.share.Fragment.FragmentMyPush;
import com.example.share.R;
import com.het.smarttab.SmartTabLayout;
import com.het.smarttab.v4.FragmentPagerItem;
import com.het.smarttab.v4.FragmentPagerItems;
import com.het.smarttab.v4.FragmentStatePagerItemAdapter;

public class MyCollection extends AppCompatActivity {

    private SmartTabLayout mMy_collection_tab;
    private ViewPager mMy_collection_viewpager;
    private FragmentStatePagerItemAdapter mFragmentStatePagerItemAdapter;
    private ImageView mMyCollectionBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        initView();
        //设置预加载最少为3个
        mMy_collection_viewpager.setOffscreenPageLimit(3);

        initEvent();
    }

    private void initEvent() {
        //定义两个数组
        String[] tabs = new String[]{"帖子","论坛"};
        //为了配置tab
        FragmentPagerItems pagerItems = new FragmentPagerItems(MyCollection.this);
        //通过for循环把帖子和论坛传进去
        for (int i = 0; i < tabs.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("name",tabs[i]);
        }
        //这块要单独设置
        pagerItems.add(FragmentPagerItem.of(tabs[0],FragmentMyPush.class));
        pagerItems.add(FragmentPagerItem.of(tabs[1],FragmentMyCommunity.class));
        //将使用全部移除
        mMy_collection_viewpager.removeAllViews();
        mFragmentStatePagerItemAdapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(),pagerItems);
        mMy_collection_viewpager.setAdapter(mFragmentStatePagerItemAdapter);
        mMy_collection_tab.setViewPager(mMy_collection_viewpager);


        mMyCollectionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mMy_collection_tab = findViewById(R.id.my_collection_tab);
        mMy_collection_viewpager = findViewById(R.id.my_collection_viewpager);
        mMyCollectionBack = findViewById(R.id.my_collection_back);
    }
}
