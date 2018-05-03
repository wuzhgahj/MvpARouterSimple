package com.wzg.core.base;



import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ch on 2017/11/27.
 */

public class BasePresenter<V extends BaseView> {

    public BasePresenter(V baseView) {
        attachView(baseView);
    }

    private V baseView;
    private CompositeDisposable mCompositeDisposable;

    /**
     * 绑定 view
     *
     * @param view
     */
    private void attachView(V view) {
        this.baseView = view;
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        onUnSubscribe();
    }

    /**
     * 判断是否绑定
     *
     * @return
     */
    public boolean isAttachView() {
        return baseView != null;
    }

    /**
     * 返回 view
     *
     * @return
     */
    public V getBaseView() {
        return baseView;
    }


    /**
     * 检查是否绑定
     */
    public void checkViewAttach() {
        if (!isAttachView()) {
            throw new MvpViewNotAttachedException();
        }
    }

    /**
     * 自定义异常
     */
    public static class MvpViewNotAttachedException extends RuntimeException {
        private MvpViewNotAttachedException() {
            super("请求数据前请先调用 attachView(View) 方法与View建立连接");
        }
    }

    public void addSubscription(Observable<?> observable, DisposableObserver subscriber) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observable
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber));
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
