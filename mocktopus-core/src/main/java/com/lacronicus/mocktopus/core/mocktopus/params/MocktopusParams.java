package com.lacronicus.mocktopus.core.mocktopus.params;

import com.lacronicus.mocktopus.core.mocktopus.options.MethodFieldOption;
import com.lacronicus.mocktopus.core.mocktopus.options.method.MethodOption;
import com.lacronicus.mocktopus.core.mocktopus.options.observable.ObservableOption;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fdoyle on 8/4/14.
 *
 * ideally, this should be immutable
 *
 * todo: break this up. observables should be in an optional modules, and errors (which would otherwise go here)
 * are too tied to retrofit to be in the core module
 */
public class MocktopusParams{

    Map<Type, List<MethodFieldOption>> fieldOptions;
    List<ObservableOption> observableOptions;
    List<MethodOption> methodOptions;


    public MocktopusParams() {
        fieldOptions = new HashMap<Type, List<MethodFieldOption>>();
        observableOptions = new ArrayList<ObservableOption>();
        methodOptions = new ArrayList<MethodOption>();

    }

    public void setFieldOptions(Map<Type, List<MethodFieldOption>> newGlobalFieldOptions) {
        this.fieldOptions = new HashMap<Type, List<MethodFieldOption>>();
        this.fieldOptions.putAll(newGlobalFieldOptions);
    }

    public Map<Type, List<MethodFieldOption>> getFieldOptions(){
        return new HashMap<Type, List<MethodFieldOption>>(fieldOptions);
    }

    public List<MethodFieldOption> getOptionsForType(Type type) {
        return fieldOptions.get(type);
    }

    public void setObservableOptions(List<ObservableOption> observableOptions) {
        this.observableOptions = new ArrayList<ObservableOption>();
        this.observableOptions.addAll(observableOptions);
    }

    public List<ObservableOption> getObservableOptions() {
        return observableOptions;
    }

    public void setMethodOptions(List<MethodOption> methodOptions) {
        this.methodOptions = new ArrayList<MethodOption>();
        this.methodOptions.addAll(methodOptions);
    }

    public List<MethodOption> getMethodOptions() {
        return methodOptions;
    }



}
