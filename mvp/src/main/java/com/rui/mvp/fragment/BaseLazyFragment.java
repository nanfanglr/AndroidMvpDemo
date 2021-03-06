package com.rui.mvp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegate;
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl;
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback;
import com.rui.mvp.basemvp.LoadMvpView;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * Created by 0200030 on 2017/9/2.
 */
public abstract class BaseLazyFragment<
        VIEW extends LoadMvpView
        , PRESENTER extends MvpPresenter<VIEW>>
        extends BaseDaggerFragment
        implements MvpDelegateCallback<VIEW, PRESENTER>, LoadMvpView {

    protected boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    protected boolean hasFetchData; // 标识已经触发过懒加载数据

    /**
     * Can't inject directly, as the presenter instantiation needs to happen by mosby in {@link this#createPresenter()}.
     */
    @Inject
    Provider<PRESENTER> presenterProvider;
    private PRESENTER presenter;


    // Delegate propagation ****************************************************************************************************************

    protected FragmentMvpDelegate<VIEW, PRESENTER> mvpDelegate;

    //        @Override
    protected FragmentMvpDelegate<VIEW, PRESENTER> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl<>(this, this
                    , true, true);
        }
        return mvpDelegate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        unbinder = ButterKnife.bind(this, view);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
        isViewPrepared = true;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，
        // fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始
        hasFetchData = false;
        isViewPrepared = false;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMvpDelegate().onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getMvpDelegate().onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    // MVP related *************************************************************************************************************************


    /**
     * Creates a new presenter instance, if needed. Will reuse the previous presenter instance if
     * {@link #setRetainInstance(boolean)} is set to true. This method will be called from
     * {@link #onViewCreated(View, Bundle)}
     */
    public PRESENTER createPresenter() {
        return presenterProvider.get();
    }

    @NonNull
    @Override
    public PRESENTER getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull PRESENTER presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public VIEW getMvpView() {
        return (VIEW) this;
    }

    //懒加载相关*********************************************************************************************************
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.v(TAG, getClass().getName() + "------>isVisibleToUser = " + isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    protected void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }

    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     */
    protected abstract void lazyFetchData();


    // Loading related *************************************************************************************************************************

    @Override
    public void showLoadingBar() {

    }

    @Override
    public void dismissLoadingBar() {

    }

    @Override
    public void showLoadingFailureError() {

    }

}
