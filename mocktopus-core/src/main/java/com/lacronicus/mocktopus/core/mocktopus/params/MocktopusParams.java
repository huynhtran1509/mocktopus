package com.lacronicus.mocktopus.core.mocktopus.params;

import android.util.Pair;

import com.lacronicus.mocktopus.core.mocktopus.options.Option;

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

    Map<Type, List<Option>> fieldOptions;

    public MocktopusParams() {
        fieldOptions = new HashMap<Type, List<Option>>();
    }

    public void setFieldOptions(Map<Type, List<Option>> newGlobalFieldOptions) {
        this.fieldOptions = new HashMap<Type, List<Option>>();
        this.fieldOptions.putAll(newGlobalFieldOptions);
    }

    public Map<Type, List<Option>> getFieldOptions(){
        return new HashMap<Type, List<Option>>(fieldOptions);
    }

    public List<Option> getOptionsForType(Type type) {
        return fieldOptions.get(type);
    }
}
