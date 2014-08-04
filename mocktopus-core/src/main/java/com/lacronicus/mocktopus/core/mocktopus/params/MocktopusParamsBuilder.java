package com.lacronicus.mocktopus.core.mocktopus.params;

import com.lacronicus.mocktopus.core.mocktopus.options.Option;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fdoyle on 8/4/14.
 */
public class MocktopusParamsBuilder {
    Map<Type, List<Option>> fieldOptions;

    public MocktopusParamsBuilder() {
        fieldOptions = new HashMap<Type, List<Option>>();
    }

    public MocktopusParamsBuilder(MocktopusParams paramsToCopy) {
        this.fieldOptions = paramsToCopy.getFieldOptions();
    }

    protected MocktopusParamsBuilder addFieldOption(Type fieldType, Option option) {
        List<Option> options;
        if(fieldOptions.containsKey(fieldType)) {
            options = fieldOptions.get(fieldType);
        } else {
            options = fieldOptions.put(fieldType, new ArrayList<Option>());
        }
        fieldOptions.get(fieldType).add(option);
        return this;
    }

    public MocktopusParamsBuilder addString(String s) {
        addFieldOption(String.class, new Option(s));
        return this;
    }

    public MocktopusParamsBuilder addInteger(Integer i) {
        addFieldOption(Integer.class, new Option(i));
        return this;
    }

    public MocktopusParamsBuilder addLong(Long b) {
        addFieldOption(Long.class, new Option(b));
        return this;
    }

    public MocktopusParamsBuilder addFloat(Float f) {
        addFieldOption(Float.class, new Option(f));
        return this;
    }

    public MocktopusParamsBuilder addDouble(Double d) {
        addFieldOption(Double.class, new Option(d));
        return this;
    }

    public MocktopusParamsBuilder addCharacter(Character c) {
        addFieldOption(Character.class, new Option(c));
        return this;
    }

    public MocktopusParamsBuilder addBoolean(Boolean b) {
        addFieldOption(Boolean.class, new Option(b));
        return this;
    }



    public MocktopusParamsBuilder defaultGlobalParams() {
        //add strings
        addString("simple string");
        addString("multi\nline\nstring");
        addString("lorem ipsum...");
        //add chars
        addCharacter('a');
        addCharacter('\n');
        addCharacter(' ');
        //add ints
        addInteger(0);
        addInteger(1);
        addInteger(9999999);
        addInteger(-1);
        addInteger(-9999999);
        //add longs
        addLong(new Long(1000));
        addLong(Long.MAX_VALUE);
        addLong(Long.MIN_VALUE);
        //add floats
        addFloat(0f);
        addFloat(Float.MAX_VALUE);
        addFloat(Float.MIN_NORMAL);
        //add doubles
        addDouble(0.0);
        addDouble(Double.MAX_VALUE);
        addDouble(Double.MIN_VALUE);
        //add bools
        addBoolean(true);
        addBoolean(false);
        return this;
    }

    public MocktopusParamsBuilder emptyParams() {
        //add nothing
        return this;
    }


    public MocktopusParams build() {
        MocktopusParams params = new MocktopusParams();
        params.setFieldOptions(fieldOptions);
        return params;
    }
}
