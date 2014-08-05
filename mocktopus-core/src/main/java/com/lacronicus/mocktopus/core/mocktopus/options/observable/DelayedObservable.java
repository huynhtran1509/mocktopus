package com.lacronicus.mocktopus.core.mocktopus.options.observable;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fdoyle on 8/4/14.
 */
public class DelayedObservable implements ObservableOption {

    public int millis;

    public DelayedObservable(int millis) {
        this.millis = millis;
    }

    @Override
    public String getDescription() {
        return "" + millis +" millisecond delay";
    }

    @Override
    public <T> Observable<T> createObservableForObject(T o) {
        return Observable.from(o).delay(millis, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());//this delay thing can be flaky. seems to work with this particular setup though
    }
}
