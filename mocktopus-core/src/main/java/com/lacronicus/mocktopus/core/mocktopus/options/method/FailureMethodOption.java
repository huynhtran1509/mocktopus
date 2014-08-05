package com.lacronicus.mocktopus.core.mocktopus.options.method;

import com.lacronicus.mocktopus.core.mocktopus.options.retrofit.ErrorBuilder;

/**
 * Created by fdoyle on 8/5/14.
 */
public class FailureMethodOption implements MethodOption {

    @Override
    public boolean shouldThrowException() {
        return true;
    }

    @Override
    public String getDescription() {
        return "generic failure";
    }

    @Override
    public RuntimeException getErrorIfAny() {
        return new ErrorBuilder().buildNetworkError().build();
    }
}
