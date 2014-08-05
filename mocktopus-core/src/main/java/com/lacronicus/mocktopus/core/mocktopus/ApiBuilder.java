package com.lacronicus.mocktopus.core.mocktopus;

import android.util.Pair;

import com.lacronicus.mocktopus.core.mocktopus.invocationhandler.MockInvocationHandler;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParams;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParamsBuilder;

import java.lang.reflect.Proxy;

import retrofit.ErrorHandler;

/**
 * Created by fdoyle on 8/4/14.
 */
public class ApiBuilder {
    MocktopusParams params;
    ErrorHandler handler;

    public ApiBuilder() {
        params = new MocktopusParamsBuilder().defaultGlobalParams().build();
    }

    public ApiBuilder withCustomParams(MocktopusParams params) {
        this.params = params;
        return this;
    }

    public ApiBuilder withRetrofitErrorHandler(ErrorHandler handler) {
        this.handler = handler;
        return this;
    }

    public <T> Pair<T, MockInvocationHandler> buildApi(Class<T> api) {
        MockInvocationHandler handler = new MockInvocationHandler(api);
        handler.setParams(params);
        T service = (T) Proxy.newProxyInstance(
                api.getClassLoader(),
                new Class[]{api},
                handler
        );
        return new Pair<T, MockInvocationHandler>(service, handler);
    }
}
