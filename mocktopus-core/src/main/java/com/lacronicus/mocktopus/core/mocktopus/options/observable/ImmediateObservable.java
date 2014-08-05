package com.lacronicus.mocktopus.core.mocktopus.options.observable;

import retrofit.RetrofitError;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fdoyle on 8/4/14.
 */
public class ImmediateObservable implements ObservableOption {

    @Override
    public String getDescription() {
        return "immediate";
    }

    @Override
    public <T> Observable<T> createObservableForObject(T o) {
        return Observable.from(o).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable createObservableForException(Exception error) {
        return Observable.error(error).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
