package com.lacronicus.mocktopus.core.mocktopus;

import android.util.Pair;

import com.lacronicus.mocktopus.core.mocktopus.options.Option;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by fdoyle on 7/16/14.
 */
public class FieldSettings extends HashMap<Pair<Method, Field>, Option>{
    //this class exists for clarity

    //make this a general settings object
    //add observable settings support
    //add List settings support

    public Option get(Method method, Field field) {
        return this.get(new Pair<Method, Field>(method, field));
    }
}
