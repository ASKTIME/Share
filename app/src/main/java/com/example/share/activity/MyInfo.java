package com.example.share.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.share.Bean.User;
import com.example.share.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MyInfo extends AppCompatActivity {

    private ImageView mBack;
    private TextView mMyInfoName;
    private TextView mMyInfoPush;
    private TextView mMyInfoCommunity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        initView();

        getInfo();

        initEvent();
    }

    private void getInfo() {
        //获取信息
        User user = BmobUser.getCurrentUser(User.class);
        String objectId = user.getObjectId();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(objectId, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e ==null) {
                    mMyInfoName.setText(user.getUsername());
                    // mMineNickname.setText(user.getNickname());

                }else{
                    Toast.makeText(MyInfo.this, "加载失败...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initEvent() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mBack = findViewById(R.id.back);
        mMyInfoName = findViewById(R.id.my_info_name);
        //多对多的关系
        mMyInfoPush = findViewById(R.id.my_info_push);
        mMyInfoCommunity = findViewById(R.id.my_info_community);
    }
}
