package com.mvp.rui.androidmvpdemo.module.di.module;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.mvp.rui.androidmvpdemo.common.activity.BaseActivity;
import com.mvp.rui.androidmvpdemo.common.dagger.modules.BaseActivityModule;
import com.mvp.rui.androidmvpdemo.common.dagger.scopes.ActivityScope;
import com.mvp.rui.androidmvpdemo.common.dagger.scopes.PerFragment;
import com.mvp.rui.androidmvpdemo.example.Test;
import com.mvp.rui.androidmvpdemo.example.TestContract;
import com.mvp.rui.androidmvpdemo.example.TestInnerImpl;
import com.mvp.rui.androidmvpdemo.module.ui.MainActivity;
import com.mvp.rui.androidmvpdemo.module.ui.fragment.ConnectionFragment;
import com.mvp.rui.androidmvpdemo.module.ui.fragment.HomeFragment;
import com.mvp.rui.androidmvpdemo.module.ui.fragment.MallFragment;
import com.mvp.rui.androidmvpdemo.module.ui.fragment.OfferFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

/**
 * 负责提供MainActivity所需要的依赖
 */
@Module(includes = BaseActivityModule.class)
public abstract class MainActModule {

    /**
     * Provides the injector for the {@link HomeFgModule}, which has access to the dependencies
     * provided by this activity and application instance (singleton scoped objects).
     */
    @PerFragment
    @ContributesAndroidInjector(modules = HomeFgModule.class)
    abstract HomeFragment homeFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector
    abstract OfferFragment offerFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector
    abstract MallFragment mallFragmentInjector();

    @PerFragment
    @ContributesAndroidInjector
    abstract ConnectionFragment connectionFragmentInjector();

    /**
     * As per the contract specified in {@link BaseActivityModule}; "This must be included in all
     * activity modules, which must provide a concrete implementation of {@link Activity}."
     * <p>
     * This provides the activity required to inject the
     * {@link BaseActivityModule#ACTIVITY_FRAGMENT_MANAGER} into the
     * {@link BaseActivity}.
     *
     * @param activity the activity
     * @return the activity
     */
    @Binds
    @ActivityScope
    abstract FragmentActivity activity(MainActivity activity);

    /**
     * 通过接口来接收类的写法，必须在实现类的构造方法上加上@inject；
     * 如果是通过本类来接收的话，不写需要此方法；
     *
     * @param testInnerImpl
     * @return
     */
    @Binds
    abstract TestContract.TestInner providesTest2(TestInnerImpl testInnerImpl);

    /**
     * provides 的对象写法
     *
     * @return
     */
    @Provides
    static Test providesTest() {
        return new Test();
    }

//    @Provides
//    @Named(BaseFragmentModule.FRAGMENT)
//    static Fragment providesHomeFragment(Context context) {
//        return HomeFragment.newInstance(context);
//    }
//
//    @Provides
//    @Named(BaseFragmentModule.FRAGMENT)
//    static OfferFragment providesOfferFragment(Context context) {
//        return OfferFragment.newInstance(context);
//    }
//
//    @Provides
//    @Named(BaseFragmentModule.FRAGMENT)
//    static MallFragment providesMallFragment(Context context) {
//        return MallFragment.newInstance(context);
//    }
//
//    @Provides
//    @Named(BaseFragmentModule.FRAGMENT)
//    static ConnectionFragment providesConnectionFragment(Context context) {
//        return ConnectionFragment.newInstance(context);
//    }
}