package com.example.share.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.share.Adapter.HomeAdapter;
import com.example.share.Bean.Post;
import com.example.share.Bean.User;
import com.example.share.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class FragmentHome extends Fragment {

    private TextView mTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    List<Post> mData;
    private HomeAdapter mHomeAdapter;
    private TextView mLoadingUser;
    private TextView mWelcomeUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //返回一个界面
        return inflater.inflate(R.layout.fragment_home,container,false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //逻辑处理

        initView();
        Bmob.initialize(getActivity(),"88d4bc881a96eac8419cb22aeff54874");

        //初始刷新
        Refresh();


        //设置系统颜色
        //mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        //监听刷新的方法
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                Refresh();
            }
        });

        //用户加载中。。。 xxx欢迎您
        //获取现在的用户
        BmobUser bmobUser = BmobUser.getCurrentUser(User.class);//User.class  继承关系
        //拿到ID
        final String userId = bmobUser.getObjectId();
        BmobQuery<User> userBmobQuery = new BmobQuery<>();
        userBmobQuery.getObject(userId, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    mLoadingUser.setText(user.getUsername());
                }else {
                    mWelcomeUser.setText(" ");
                    mLoadingUser.setText(" ");
                }
            }
        });

    }

    private void Refresh() {
        //对recyclerView的视图进行刷新
        //从比目后台查询一些信息
        //通过Post来进行
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        //逆序，从现在到以前
        postBmobQuery.order("-createdAt");
        postBmobQuery.setLimit(1000);
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                //要设置刷新停止
                mSwipeRefreshLayout.setRefreshing(false);
                if (e ==null) {
                    mData = list;
                    //设置适配器的一些操作
                    mHomeAdapter = new HomeAdapter(getActivity(),mData);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mHomeAdapter);
                }else{
                    //出现异常
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "获取数据失败" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        mRecyclerView = getActivity().findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = getActivity().findViewById(R.id.swipe);
        mTextView = getActivity().findViewById(R.id.fragment_home);
        mLoadingUser = getActivity().findViewById(R.id.loading_user);
        mWelcomeUser = getActivity().findViewById(R.id.welcome_user);
    }
}
