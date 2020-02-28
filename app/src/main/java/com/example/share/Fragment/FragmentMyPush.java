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
import com.example.share.Adapter.MyPushCollectionAdapter;
import com.example.share.Bean.Post;
import com.example.share.Bean.User;
import com.example.share.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentMyPush extends Fragment {

    private SwipeRefreshLayout mFragmentMyPushSwipe;
    private RecyclerView mFragmentMyPushRecycle;
    List<Post> mPosts;
    private MyPushCollectionAdapter mMyPushCollectionAdapter;
    private TextView mMyPushCollectionEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_push,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //逻辑的处理
        initView();


        Refresh();

        //设置系统颜色
        //mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        mFragmentMyPushSwipe.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        mFragmentMyPushSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void Refresh() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        postBmobQuery.addWhereEqualTo("mRelation",user);
        //进行查询
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                //设置不管查询是否成功都要进行刷新
                mFragmentMyPushSwipe.setRefreshing(false);
                if (e == null) {
                    if (list.size()>0) {
                        mFragmentMyPushSwipe.setVisibility(View.VISIBLE);

                        mPosts = list;
                        //设置适配器的一些操作
                        mMyPushCollectionAdapter = new MyPushCollectionAdapter(mPosts,getActivity());
                        mFragmentMyPushRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mFragmentMyPushRecycle.setAdapter(mMyPushCollectionAdapter);
                    }else {
                        mMyPushCollectionEmpty.setVisibility(View.VISIBLE);
                        mFragmentMyPushSwipe.setVisibility(View.GONE);
                    }
                }else {
                    Toast.makeText(getActivity(), "获取收藏帖子失败" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        mFragmentMyPushSwipe = getActivity().findViewById(R.id.fragment_my_push_swipe);
        mFragmentMyPushRecycle = getActivity().findViewById(R.id.fragment_my_push_recycle);
        mMyPushCollectionEmpty = getActivity().findViewById(R.id.my_push_collection_empty);
    }
}
