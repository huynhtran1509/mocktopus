package com.lacronicus.mocktopus.core.mocktopus.invocationhandler;

import com.lacronicus.mocktopus.core.mocktopus.Settings;
import com.lacronicus.mocktopus.core.mocktopus.FlattenedOptions;
import com.lacronicus.mocktopus.core.mocktopus.ObjectCreator;
import com.lacronicus.mocktopus.core.mocktopus.options.Options;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParams;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParamsBuilder;
import com.lacronicus.mocktopus.core.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by fdoyle on 7/10/14.
 */
public class MockInvocationHandler implements InvocationHandler {

    Options options;
    Settings settings;
    Class api;


    public MockInvocationHandler(Class api) {
        this.api = api;
        MocktopusParams params = new MocktopusParamsBuilder().defaultGlobalParams().build();

        FieldOptionsListBuilder builder = new FieldOptionsListBuilder(params);
        options = new Options(builder, api);
        settings = options.getDefaultFieldSettings();
        //use api to build options
    }

    public void setParams(MocktopusParams params) {
        FieldOptionsListBuilder builder = new FieldOptionsListBuilder(params);
        options = new Options(builder, api);
        settings = options.getDefaultFieldSettings();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return new ObjectCreator().createObject(method.getGenericReturnType(), method, null, settings);
    }

    public Options getOptions() {
        return options;
    }

    public FlattenedOptions getFlattenedOptions() {
        return options.flatten();
    }

    public Settings getSettings() {
        return settings;
    }


}
