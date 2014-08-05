package com.lacronicus.mocktopus.core.mocktopus.options.retrofit;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedString;

/**
 * Created by fdoyle on 8/5/14.
 */
public class ErrorBuilder {
    public void buildError(Type type) {
        RetrofitError.httpError("www.mocktopus.com",new Response("der",401,"reason",null, new TypedString("derp")), new GsonConverter(new Gson()), type);
    }
}
