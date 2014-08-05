package com.lacronicus.mocktopus.core.mocktopus;

import android.util.Pair;

import com.lacronicus.mocktopus.core.mocktopus.options.MethodFieldOption;
import com.lacronicus.mocktopus.core.mocktopus.options.observable.ImmediateObservable;
import com.lacronicus.mocktopus.core.mocktopus.options.observable.ObservableOption;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fdoyle on 7/16/14.
 */
public class Settings{

    Map<Pair<Method, Field>, MethodFieldOption> fieldSettings;
    ObservableOption observableOption = new ImmediateObservable();
    Map<Method, MethodFieldOption> methodOptions;

    public Settings() {
        fieldSettings = new HashMap<Pair<Method, Field>, MethodFieldOption>();
    }

    //make this a general settings object
    //add observable settings support
    //add List settings support

    public MethodFieldOption put(Method method, Field field, MethodFieldOption option) {
        return fieldSettings.put(new Pair<Method, Field>(method, field), option);
    }

    public MethodFieldOption get(Method method, Field field) {
        return fieldSettings.get(new Pair<Method, Field>(method, field));
    }

}
