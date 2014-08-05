package com.lacronicus.mocktopus.core.mocktopus.options.method;

/**
 * Created by fdoyle on 8/5/14.
 */
public class SuccessMethodOption implements MethodOption {

    @Override
    public boolean shouldThrowException() {
        return false;
    }

    @Override
    public String getDescription() {
        return "success";
    }

    @Override
    public RuntimeException getErrorIfAny() {
        return null;
    }
}


