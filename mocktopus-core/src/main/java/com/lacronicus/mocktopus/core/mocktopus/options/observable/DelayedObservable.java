package com.lacronicus.mocktopus.core.mocktopus.options.observable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fdoyle on 8/4/14.
 */
public class DelayedObservable implements ObservableOption {

    @Override
    public String getDescription() {
        return "5 second delay";
    }

    @Override
    public <T> Observable<T> createObservableForObject(T o) {
        return Observable.from(o).delay(5000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());//this delay thing can be flaky. seems to work with this particular setup though
    }
}
