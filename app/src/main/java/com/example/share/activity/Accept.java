package com.example.share.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.share.Bean.Post;
import com.example.share.Bean.User;
import com.example.share.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


public class Accept extends AppCompatActivity {

    private TextView mUsername;
    private TextView mContent;
    private TextView mTime;
    private ImageView mBack;
    private ImageView mScNormal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);


        //初始化视图
        initView();
        initData();

        getRelated();
        //监听返回
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //监听收藏的动作
        mScNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断一下有没有被收藏
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                BmobQuery<Post> postBmobQuery = new BmobQuery<>();
                postBmobQuery.getObject(id, new QueryListener<Post>() {
                    @Override
                    public void done(Post post, BmobException e) {
                        //判断是否被收藏//图标为白色
                        if (post.getIsrelated().equals("0")) {
                            Intent intent = getIntent();
                            String id = intent.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Post posts = new Post();
                            posts.setObjectId(id);
                            posts.setIsrelated("1");
                            BmobRelation bmobRelation = new BmobRelation();
                            bmobRelation.add(user);

                            posts.setRelation(bmobRelation);
                            posts.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e ==null) {
                                        mScNormal.setImageResource(R.drawable.sc_press);
                                        Toast.makeText(Accept.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Accept.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Intent intent = getIntent();
                            String id = intent.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Post posts = new Post();
                            posts.setObjectId(id);
                            posts.setIsrelated("0");
                            BmobRelation bmobRelation = new BmobRelation();
                            bmobRelation.remove(user);

                            posts.setRelation(bmobRelation);
                            posts.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e ==null) {
                                        mScNormal.setImageResource(R.drawable.sc_normal);
                                        Toast.makeText(Accept.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Accept.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void getRelated() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        postBmobQuery.getObject(id, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                if (post.getIsrelated().equals("1")) {
                    //已经被收藏
                    mScNormal.setImageResource(R.drawable.sc_press);
                }else {
                    //无收藏

                }
            }
        });
    }

    private void initData() {

        //第二种写法
        Intent username = getIntent();
        Intent content = getIntent();
        Intent time = getIntent();
        //从HomeAdapter把数据传进来
        //预加载数据进来
        String usernameStringExtra = username.getStringExtra("username");
        String contentStringExtra= content.getStringExtra("content");
        String timeStringExtra = time.getStringExtra("time");
        mUsername.setText(usernameStringExtra);
        mContent.setText(contentStringExtra);
        mTime.setText(timeStringExtra);





/*
       第一种方法
       //接收的数据
        // 根据id来进行数据的确认
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        //Post post = new Post();

        BmobQuery<Post> query = new BmobQuery<>();
        query.getObject(id, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                if (e==null) {
                    mCommunity_name.setText(post.getUsername());
                    mCommunity_info.setText(post.getContent());
                    mMyPushTime.setText(post.getCreatedAt());
                }else {
                    Toast.makeText(Accept.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/


    }

    private void initView() {
        mUsername = findViewById(R.id.username);
        mContent = findViewById(R.id.content);
        mTime = findViewById(R.id.time);
        mBack = findViewById(R.id.back);
        mScNormal = findViewById(R.id.sc_normal);
    }
}
