package com.lacronicus.mocktopus.core.mocktopus.options.observable;

import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by fdoyle on 8/4/14.
 */
public interface ObservableOption {

    public String getDescription();

    public <T> Observable<T> createObservableForObject(T o);

    public  Observable createObservableForException(Exception error); //should this be the more generic error or a RetrofitError
}
