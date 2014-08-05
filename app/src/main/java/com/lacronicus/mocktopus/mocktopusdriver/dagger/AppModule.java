package com.lacronicus.mocktopus.mocktopusdriver.dagger;

import android.app.Application;
import android.content.Context;
import android.util.Pair;

import com.google.gson.Gson;
import com.lacronicus.mocktopus.core.mocktopus.ApiBuilder;
import com.lacronicus.mocktopus.core.mocktopus.Mocktopus;
import com.lacronicus.mocktopus.core.mocktopus.invocationhandler.MockInvocationHandler;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParams;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParamsBuilder;
import com.lacronicus.mocktopus.mocktopusdriver.MainActivity;
import com.lacronicus.mocktopus.mocktopusdriver.MainApp;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.FakeService;
import com.lacronicus.mocktopus.mocktopusdriver.redditservice.RedditService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(injects = {
        Injector.class,
        MainApp.class,
        MainActivity.class
}, library = true, complete = true)
public class AppModule {
    private final Application application;
    private final Injector injector;

    public AppModule(Application application) {
        this.application = application;
        this.injector = new Injector(application);
    }

    /**
     * Allow the application context to be injected but require that it be annotated with {@link
     * ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Injector providesInjector() {
        return injector;
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }


    @Provides
    @Singleton
    FakeService provideFakeService() {
        MocktopusParams params = new MocktopusParamsBuilder()
                .defaultGlobalParams()
                .addString("Custom Service String")
                .build();

        Pair<FakeService, MockInvocationHandler> pair =
                new ApiBuilder()
                        .withCustomParams(params)
                        .buildApi(FakeService.class);
        return Mocktopus.getInstance().addApi(FakeService.class, pair);
    }


    @Provides
    @Singleton
    RedditService provideRedditService() {
        Pair<RedditService, MockInvocationHandler> pair = new ApiBuilder().buildApi(RedditService.class);
        return Mocktopus.getInstance().addApi(RedditService.class, pair);
    }

}

