package com.lacronicus.mocktopus.core.mocktopus.options.observable;

import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by fdoyle on 8/4/14.
 */
public class NeverObservable implements ObservableOption {

    @Override
    public String getDescription() {
        return "never return";
    }

    @Override
    public <T> Observable<T> createObservableForObject(T o) {
        return Observable.never();
    }


    @Override
    public Observable createObservableForException(Exception error) {
        return Observable.never();
    }
}
