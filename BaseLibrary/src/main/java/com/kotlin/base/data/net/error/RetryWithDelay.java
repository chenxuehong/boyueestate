package com.kotlin.base.data.net.error;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {

    private static final String TAG = "FunctionRetryWhenUnauthorized";

    private int maxRetries = 3;
    private long retryDelayMillis = 1000;
    private int currentRetryCount = 0;

    public RetryWithDelay() {
    }

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                return ++currentRetryCount <= maxRetries ? Observable.timer(retryDelayMillis * maxRetries, TimeUnit.MILLISECONDS) : Observable.error(throwable);
            }
        });
    }
}
