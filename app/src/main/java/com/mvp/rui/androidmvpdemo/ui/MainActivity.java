package com.mvp.rui.androidmvpdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvp.rui.androidmvpdemo.R;
import com.mvp.rui.androidmvpdemo.di.contract.MainActView;
import com.mvp.rui.androidmvpdemo.di.presenter.MainActPresenter;
import com.mvp.rui.androidmvpdemo.example.Test;
import com.mvp.rui.androidmvpdemo.example.TestContract;
import com.mvp.rui.androidmvpdemo.ui.fragment.ConnectionFragment;
import com.mvp.rui.androidmvpdemo.ui.fragment.HomeFragment;
import com.mvp.rui.androidmvpdemo.ui.fragment.MallFragment;
import com.mvp.rui.androidmvpdemo.ui.fragment.OfferFragment;
import com.rui.mvp.activity.BaseActivity;
import com.rui.common.constant.APPValue;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by rui on 2018/4/3
 * 2018.2.10 上传GitHub测试
 */
public class MainActivity extends BaseActivity<
        MainActView
        , MainActPresenter>
        implements
        MainActView
        , RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.view_mainapp)
    View viewMainapp;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_mall)
    RadioButton rbIndex;
    @BindView(R.id.rb_offer)
    RadioButton rbOffer;
    @BindView(R.id.rb_connections)
    RadioButton rbConnections;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.iv_connetion_msgcount)
    TextView ivConnetionMsgcount;
    @BindView(R.id.rl_home)
    RelativeLayout rlHome;

//通过注入的方式fragment不能马上attached到activity，因此无法获得childfragment的fragmentmanager对象
//    @Inject
//    @Named(BaseFragmentModule.FRAGMENT)
//    Fragment homeFragment;
//    @Inject
//    @Named(BaseFragmentModule.FRAGMENT)
//    MallFragment mallFragment;
//    @Inject
//    @Named(BaseFragmentModule.FRAGMENT)
//    OfferFragment offerFragment;
//    @Inject
//    @Named(BaseFragmentModule.FRAGMENT)
//    ConnectionFragment connectionFragment;

    @Inject
    Test test;
    //这个是通过接口接收的，不能通过实现类上面的构造来注入
    //如果是通过本类来接收的，可以直接通过构造来注入
    @Inject
    TestContract.TestInner test2;

    private Fragment mCurrentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchMenu(getFragmenTag(R.id.rb_home));
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void switchMenu(String fragmentTag) {

        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);

        if (fragment != null) {
            if (fragment == mCurrentFragment) return;
            fragmentManager.beginTransaction().show(fragment).commit();
        } else {
            if (TextUtils.equals(fragmentTag, APPValue.FRAGMENT_HOME)) {
                //加载首页
                fragment = HomeFragment.newInstance(this);
            } else if (TextUtils.equals(fragmentTag, APPValue.FRAGMENT_OFFER)) {
                //加载报价
                fragment = OfferFragment.newInstance(this);
            } else if (TextUtils.equals(fragmentTag, APPValue.FRAGMENT_Mall)) {
                //加载商城
                fragment = MallFragment.newInstance(this);
            } else if (TextUtils.equals(fragmentTag, APPValue.FRAGMENT_CONNECTIONS)) {
                //加载人脉
                fragment = ConnectionFragment.newInstance(this);
            }
            if (fragment != null) {
                fragmentManager.beginTransaction().add(R.id.fl_container
                        , fragment, fragmentTag).commit();
            }
        }
        if (mCurrentFragment != null) {
            fragmentManager.beginTransaction().hide(mCurrentFragment).commit();
        }
        mCurrentFragment = fragment;
    }

    private String getFragmenTag(int menuId) {
        switch (menuId) {
            case R.id.rb_home:
                return APPValue.FRAGMENT_HOME;
            case R.id.rb_offer:
                return APPValue.FRAGMENT_OFFER;
            case R.id.rb_mall:
                return APPValue.FRAGMENT_Mall;
            case R.id.rb_connections:
                return APPValue.FRAGMENT_CONNECTIONS;
            default:
                return "";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switchMenu(getFragmenTag(checkedId));
    }

    @OnClick({R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_publish:
                startActivity(new Intent(this, UserExampleActivity.class));
                break;
        }
    }
}
