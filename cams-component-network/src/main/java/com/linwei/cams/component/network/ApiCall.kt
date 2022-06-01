package com.linwei.cams.component.network

import android.content.Context
import com.linwei.cams.component.network.annotation.RetryCount
import com.linwei.cams.component.network.annotation.RetryDelay
import com.linwei.cams.component.network.annotation.RetryIncreaseDelay
import com.linwei.cams.component.network.callback.ApiCallback
import com.linwei.cams.component.network.factory.rxjava.CallEnqueueObservable
import com.linwei.cams.component.network.factory.rxjava.RetryHandler
import com.linwei.cams.component.network.model.ApiResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ApiCall<R> internal constructor(
    annotations: Array<Annotation>,
    private var call: Call<ApiResponse<R>>
) {
    private var mRetryCount = 0
    private var mRetryDelay: Long = 0
    private var mRetryIncreaseDelay: Long = 0

    private val mEnqueueObservable: Observable<Response<ApiResponse<R>>> =
        RxJavaPlugins.onAssembly(CallEnqueueObservable(call))

    private var mDisposable: Disposable? = null

    init {
        for (annotation in annotations) {
            val clazz: Class<out Annotation> = annotation.javaClass
            if (clazz == RetryCount::class.java) {
                val retryCount: RetryCount = annotation as RetryCount
                mRetryCount = retryCount.value
            } else if (clazz == RetryDelay::class.java) {
                val retryDelay: RetryDelay = annotation as RetryDelay
                mRetryDelay = retryDelay.value
            } else if (clazz == RetryIncreaseDelay::class.java) {
                val retryIncreaseDelay: RetryIncreaseDelay = annotation as RetryIncreaseDelay
                mRetryIncreaseDelay = retryIncreaseDelay.value
            }
        }
    }

    /**
     * 进入请求队列
     * 带可取消进度框，有toast
     * 自动绑定activity生命周期
     *
     * @param callback 请求回调
     */
    fun <T : ApiCallback<R>?> enqueue(activity: Context?, callback: T?) {
        enqueue(activity, ProgressType.CANCELABLE, true, callback)
    }

    /**
     * 进入请求队列
     *
     * @param activity     界面
     * @param progressType 进度框类型
     * @param toastError   是否弹错误toast
     * @param callback     请求回调
     */
    fun <T : ApiCallback<R>?> enqueue(
        activity: Context?,
        type: ProgressType = ProgressType.NONE,
        toastError: Boolean = false,
        callback: T?
    ) {
        /*if (activity instanceof RxAppCompatActivity) {
            RxAppCompatActivity rxAppCompatActivity = (RxAppCompatActivity) activity;
            observable = mEnqueueObservable.compose(rxAppCompatActivity.<Response<ApiResponse<R>>>bindToLifecycle());
        } else {
            observable = mEnqueueObservable;
        }*/
        val observable: Observable<Response<ApiResponse<R>>> = mEnqueueObservable
        mDisposable =
            observable.retryWhen(RetryHandler(mRetryCount, mRetryDelay, mRetryIncreaseDelay))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { callback?.onStart() }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<Response<ApiResponse<R>>> {
                    override fun accept(t: Response<ApiResponse<R>>) {
                        @Throws(Exception::class)
                        fun accept(response: Response<ApiResponse<R>?>) {
                            val body: ApiResponse<R>? = response.body()
                            if (!response.isSuccessful() || body == null) {
                                onError(callback, HttpException(response), toastError)
                                cancel()
                                return
                            }
                            callback?.onSuccess(body)
                            cancel()
                        }
                    }
                }) { throwable ->
                    onError(callback, throwable, toastError)
                    cancel()
                }
    }

    /**
     * Synchronously send the request and return its response.
     *
     * @throws IOException if a problem occurred talking to the server.
     */
    @Throws(IOException::class)
    fun exectue(): Response<ApiResponse<R>> {
        return call.clone().execute()
    }

    /**
     * 处理错误
     *
     * @param callback  回调
     * @param throwable 错误
     */
    private fun onError(callback: ApiCallback<R>?, throwable: Throwable, toast: Boolean) {
        callback?.onError(throwable)
    }

    fun cancel() {
        mDisposable?.dispose()
    }

    /**
     * 进度条类型
     */
    enum class ProgressType {
        /**
         * 无进度条
         */
        NONE,

        /**
         * 可取消进度条
         */
        CANCELABLE,

        /**
         * 不可取消进度条
         */
        UN_CANCELABLE
    }
}