package com.lacronicus.mocktopus.core.mocktopus.options.method;

import retrofit.RetrofitError;

/**
 * Created by fdoyle on 8/5/14.
 */
public interface MethodOption {

    public boolean shouldThrowException();

    public String getDescription();

    public RuntimeException getErrorIfAny();
}
