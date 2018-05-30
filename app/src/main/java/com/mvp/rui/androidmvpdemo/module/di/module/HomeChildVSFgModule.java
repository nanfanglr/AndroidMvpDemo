package com.mvp.rui.androidmvpdemo.module.di.module;


import android.support.v4.app.Fragment;

import com.mvp.rui.androidmvpdemo.common.dagger.modules.BaseChildFragmentModule;
import com.mvp.rui.androidmvpdemo.common.dagger.scopes.ChildFragmentScope;
import com.mvp.rui.androidmvpdemo.module.ui.fragment.HomeChildVSFragment;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;

/**
 * 负责提供HomeChildFragment所需要的依赖
 */
@Module(includes = BaseChildFragmentModule.class)
public abstract class HomeChildVSFgModule {

    /**
     * As per the contract specified in {@link BaseChildFragmentModule}; "This must be included in
     * all child fragment modules, which must provide a concrete implementation of the child
     * {@link Fragment} and named {@link BaseChildFragmentModule#CHILD_FRAGMENT}..
     *
     * @param homeChildFragment the example 3 child fragment
     * @return the fragment
     */
    @Binds
    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
    @ChildFragmentScope
    abstract Fragment fragment(HomeChildVSFragment homeChildFragment);


}