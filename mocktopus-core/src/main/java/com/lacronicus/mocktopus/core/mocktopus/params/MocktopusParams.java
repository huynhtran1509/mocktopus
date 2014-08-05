package com.lacronicus.mocktopus.core.mocktopus.params;

import com.lacronicus.mocktopus.core.mocktopus.options.MethodFieldOption;
import com.lacronicus.mocktopus.core.mocktopus.options.observable.ObservableOption;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fdoyle on 8/4/14.
 *
 * todo rename this to something more descriptive,
 * "params" is too generic to really say what this does or where it's used
 *
 * this is immutable
 *
 * //todo this is where error setting goes
 */
public class MocktopusParams{

    Map<Type, List<MethodFieldOption>> fieldOptions;

    public MocktopusParams() {
        fieldOptions = new HashMap<Type, List<MethodFieldOption>>();
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
}
