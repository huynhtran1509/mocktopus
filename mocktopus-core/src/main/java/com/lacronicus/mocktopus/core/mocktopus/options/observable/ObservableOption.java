package com.lacronicus.mocktopus.core.mocktopus.options.observable;

import rx.Observable;

/**
 * Created by fdoyle on 8/4/14.
 */
public interface ObservableOption {

    public String getDescription();

    public <T> Observable<T> createObservableForObject(T o);
}
