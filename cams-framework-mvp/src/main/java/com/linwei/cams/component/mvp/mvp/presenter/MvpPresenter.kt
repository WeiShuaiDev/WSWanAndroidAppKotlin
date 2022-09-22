package com.linwei.cams.component.mvp.mvp.presenter

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer


/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/4
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 `Presenter` 接口实现
 * -----------------------------------------------------------------------
 */
abstract class MvpPresenter<V : IMvpView,M : IMvpModel>(
    private var rootView: V?,
    private var model: M?
) : IMvpPresenter, DefaultLifecycleObserver, Consumer<Disposable> {

    private var mCompositeDisposable: CompositeDisposable? = null

    init {
        rootView?.let { v ->
            if (v is LifecycleOwner) {
                v.lifecycle.addObserver(this)
                model?.let { m ->
                    if (m is DefaultLifecycleObserver) {
                        v.lifecycle.addObserver(m)
                    }
                }
            }
        }
    }


    override fun onStart() {

    }

    protected open fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let {
            mCompositeDisposable!!.add(it)
        }
    }

    override fun accept(disposable: Disposable?) {
        addDisposable(disposable)
    }

    override fun onDestroy() {
        model?.onDestroy()
        model = null

        if (mCompositeDisposable != null && !mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable!!.clear()
        }

        rootView = null
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}