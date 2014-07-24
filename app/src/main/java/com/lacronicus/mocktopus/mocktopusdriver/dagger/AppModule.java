package com.lacronicus.mocktopus.mocktopusdriver.dagger;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.lacronicus.mocktopus.mocktopusdriver.MainActivity;
import com.lacronicus.mocktopus.mocktopusdriver.MainApp;
import com.lacronicus.mocktopus.mocktopusdriver.fakeservice.FakeService;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.ConfigurationActivity;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.MockInvocationHandler;
import com.lacronicus.mocktopus.mocktopusdriver.mocktopus.Mocktopus;
import com.lacronicus.mocktopus.mocktopusdriver.redditservice.RedditService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(injects = {
        Injector.class,
        MainApp.class,
        MainActivity.class,
        ConfigurationActivity.class
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
    Mocktopus provideMocktopus() {
        return new Mocktopus();
    }

    @Provides
    @Singleton
    FakeService provideFakeService(Mocktopus mocktopus) {
        return mocktopus.initApi(FakeService.class);
    }


    @Provides
    @Singleton
    RedditService provideRedditService(Mocktopus mocktopus) {
        return mocktopus.initApi(RedditService.class);
    }

}

