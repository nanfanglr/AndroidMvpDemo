package com.mvp.rui.androidmvpdemo.di.presenter;


import com.mvp.rui.androidmvpdemo.di.contract.HomeFgView;
import com.mvp.rui.androidmvpdemo.mapper.HomeMapper;
import com.mvp.rui.androidmvpdemo.netservice.HomeService;
import com.rui.mvp.basemvp.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rui on 2018/3/9.
 */
public class HomeFgPresenter extends BasePresenter<HomeFgView> {

    private HomeService homeService;
    private HomeMapper homeMapper;

    @Inject
    public HomeFgPresenter(HomeService homeService, HomeMapper homeMapper) {
        this.homeService = homeService;
        this.homeMapper = homeMapper;
    }

    public void getCategory(String token) {
        composite.add(homeService.getCategorys(token)
                .flatMapObservable(listResultModel ->
                        Observable.fromIterable(listResultModel.getData()))
                .map(infoCategory -> {
                    //infoCategory处理一些事物
                    return infoCategory;
                })
                .toSortedList((o1, o2) -> {
                    //排序后重新变成list
                    double dis = o1.getSeqNum() - o2.getSeqNum();
                    return dis > 0 ? 1 : dis < 0 ? -1 : 0;
                })
                .map(infoCategorys -> homeMapper.map(infoCategorys))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    getView().onCategoryLoad(model);
                }, throwable -> {
                    throwable.printStackTrace();
                })
        );
    }

}
