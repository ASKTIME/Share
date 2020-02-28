package com.example.share.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.share.Adapter.MyPushAdapter;
import com.example.share.Bean.Post;
import com.example.share.Bean.User;
import com.example.share.MainActivity;
import com.example.share.R;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyPush extends AppCompatActivity {


    private SwipeMenuRecyclerView mMy_push_swipe_menu;
    private SwipeRefreshLayout mMy_push_swipe;
    private TextView mMy_push_empty;
    private ImageView mMy_push_back;


    List<Post> mPosts;
    MyPushAdapter mMyPushAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_push);


        initView();
        Bmob.initialize(MyPush.this,"88d4bc881a96eac8419cb22aeff54874");
        initEvent();

        Refresh();
    }

    private void Refresh() {
        //刷新帖子的动作

        //获取我发布的帖子 1 对多的关系
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        postBmobQuery.addWhereEqualTo("author",user);
        postBmobQuery.order("-createdAt");
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                //设置不管查询是否成功都要进行刷新
                mMy_push_swipe.setRefreshing(false);
                //进行查询作者，然后显示帖子
                if (e == null) {
                    mPosts = list;
                    if (mPosts.size() > 0) {
                        //成功了，把帖子的内容设置为可视化
                        mMy_push_swipe.setVisibility(View.VISIBLE);
                        //设置 SwipeMenuRecyclerView
                        mMy_push_swipe_menu.addItemDecoration(new DefaultItemDecoration(Color.GRAY));
                        //向右滑动的时候弹出一个删除提示框
                        /*mMy_push_swipe_menu.setSwipeMenuCreator(swipeMenuCreator);
                        mMy_push_swipe_menu.setSwipeItemClickListener(swipeMenuItemClickListener);*/
                        mMy_push_swipe_menu.setSwipeMenuCreator(swipeMenuCreator);
                        mMy_push_swipe_menu.setSwipeMenuItemClickListener(swipeMenuItemClickListener);


                        //进行实例化
                        mMyPushAdapter = new MyPushAdapter(MyPush.this,mPosts);
                        mMy_push_swipe_menu.setLayoutManager(new LinearLayoutManager(MyPush.this));
                        mMy_push_swipe_menu.setAdapter(mMyPushAdapter);

                    }else {
                        //设置没有发布帖子的提示为可视化
                        mMy_push_empty.setVisibility(View.VISIBLE);
                    }
                }else {
                  //  Toast.makeText(MyPush.this, "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void initEvent() {
        //获取我发布的帖子（本质上是一种查询）
        //刷新动作
        mMy_push_swipe.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        mMy_push_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

        mMy_push_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    // 设置菜单监听器。
    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        // 创建菜单：
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(MyPush.this)
                    .setTextColor(Color.WHITE)
                    .setBackgroundColor(Color.RED)
                    .setText("删除")
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };


    // 菜单点击监听。
    SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection();//左边还是右边菜单
            final int adapterPosition = menuBridge.getAdapterPosition();//    recyclerView的Item的position。
            int position = menuBridge.getPosition();// 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {

                Post post = new Post();
                post.setObjectId(mPosts.get(adapterPosition).getObjectId());
                post.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            mPosts.remove(adapterPosition);//删除item
                            mMyPushAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(MyPush.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    };

    private void initView() {

        mMy_push_swipe_menu = findViewById(R.id.my_push_recyclerview);
        mMy_push_swipe = findViewById(R.id.my_push_swipe);
        mMy_push_empty = findViewById(R.id.my_push_empty);
        mMy_push_back = findViewById(R.id.my_push_back);
    }
}
