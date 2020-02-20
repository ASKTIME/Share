package com.example.share.Fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    //抽取成基类，把他需要的东西存起来

    public Context mContext;  //上下文
    public Resources mResources; //资源
    public LayoutInflater mLayoutInflater; //布局

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mResources = context.getResources();
        this.mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //抽取
    private View RootView; //缓存的fragment

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (RootView == null) {
            //这里自己重写方法
            RootView = inflater.inflate(getLayoutID(), container, false);
        }

        //这里需要强制转换
        //在其他的一些版本并不会container，所以需要一些判断
        ViewGroup parent = (ViewGroup) RootView.getParent();
        if (parent != null) {
            //缓存不为空，把缓存移掉
            parent.removeView(RootView);
        }
        return RootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        bindEvent();
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    //VV View类型  id 控件的id
    protected <VV extends View> VV findView(View view, @IdRes int id){
        return view.findViewById(id);
    }

    //找到RootView的id
    protected <VV extends View> VV findView( @IdRes int id){
        return RootView.findViewById(id);
    }

    protected abstract void initData();

    protected abstract void bindEvent();


    protected abstract void initView(View view);

    //需要被子类重写要用抽象类
    protected abstract int getLayoutID();
}
