package com.lacronicus.mocktopus.core.mocktopus.options.retrofit;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedString;
import rx.Observable;

/**
 * Created by fdoyle on 8/5/14.
 */
public class ErrorBuilder {
    public ErrorBuilder() {

    }

    public NetworkErrorBuilder buildNetworkError() {
        return new NetworkErrorBuilder();
    }

    public HttpErrorBuilder buildHttpError(Converter converter, Type successType) {
        return new HttpErrorBuilder(converter, successType);
    }

    public static class NetworkErrorBuilder {
        String url = "www.google.com";
        IOException exception = new UnknownHostException("Unable to herp the derp");
        public NetworkErrorBuilder() {
        }

        public NetworkErrorBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public NetworkErrorBuilder setException(IOException exception) {
            this.exception = exception;
            return this;
        }

        public RetrofitError build() {
            return RetrofitError.networkError(url, exception);
        }
    }

    public static class HttpErrorBuilder {
        //todo fill this out
        String url = "www.google.com";
        int status = 500;
        String reason = "An error occurred";
        List<Header> headers = null;
        TypedString body = new TypedString("{}");
        Converter converter;
        Type successType;

        public HttpErrorBuilder(Converter converter, Type successType) {
            this.converter = converter;
            this.successType = successType;
        }

        public HttpErrorBuilder setStatus(int status) {
            this.status = status;
            return this;
        }

        public HttpErrorBuilder setBody(TypedString body) {
            this.body = body;
            return this;
        }

        public RetrofitError build(){
            return RetrofitError.httpError(url, new Response(url, status, reason, headers, body), converter, successType);
        }

    }
}
