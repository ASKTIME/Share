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
import android.widget.Toast;

import com.example.share.Adapter.ChatAdapter;
import com.example.share.Adapter.HomeAdapter;
import com.example.share.Bean.Community;
import com.example.share.Bean.Post;
import com.example.share.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentChat extends Fragment {
    //论坛界面


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ChatAdapter mChatAdapter;

    private List<Community> mCommunities;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initView();
        Bmob.initialize(getActivity(),"88d4bc881a96eac8419cb22aeff54874");
        //初始刷新
        Refresh();
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        initEvent();

    }

    private void Refresh() {
        //初始进入界面的时候就刷新
        //对recyclerView的视图进行刷新
        //从比目后台查询一些信息
        //通过Post来进行
        BmobQuery<Community> communityBmobQuery = new BmobQuery<>();
        //逆序，从现在到以前
        communityBmobQuery.order("-createdAt");
        communityBmobQuery.setLimit(1000);
        communityBmobQuery.findObjects(new FindListener<Community>() {
            @Override
            public void done(List<Community> list, BmobException e) {
                //要设置刷新停止
                mSwipeRefreshLayout.setRefreshing(false);
                if (e ==null) {
                    mCommunities = list;
                    //设置适配器的一些操作
                    mChatAdapter = new ChatAdapter(getActivity(),mCommunities);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mChatAdapter);
                }else{
                    //出现异常
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "获取数据失败" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initEvent() {
        //监听刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void initView() {
        mRecyclerView = getActivity().findViewById(R.id.chat_recycler_view);
        mSwipeRefreshLayout = getActivity().findViewById(R.id.chat_swipe);
    }
}
