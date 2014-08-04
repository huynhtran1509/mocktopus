package com.lacronicus.mocktopus.core.mocktopus;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Pair;

import com.lacronicus.mocktopus.core.mocktopus.invocationhandler.MockInvocationHandler;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParams;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by fdoyle on 7/16/14.
 */
public class Mocktopus {
    public static final int CONFIG_REQUEST_CODE = 999;

    static Mocktopus mocktopus;

    public Map<Type, Object> services;

    public Map<Type, MockInvocationHandler> handlers;

    MocktopusParams params;

    private Mocktopus() {
        handlers = new HashMap<Type, MockInvocationHandler>();
        services = new HashMap<Type, Object>();
    }


    public static Mocktopus getInstance() {
        if(mocktopus == null) {
            mocktopus = new Mocktopus();
        }
        return mocktopus;
    }

    public void setGlobalParams(MocktopusParams params) {
        //todo make sure this is done
    }



    /*public <T> T initApi(Class<T> api) {
        //Pair<T, MockInvocationHandler> pair = buildApi(api);
        Pair<T, MockInvocationHandler> api = new ApiBuilder();
        services.put(api,pair.first);
        handlers.put(api,pair.second);
        return pair.first;
    }*/

    public <T> T addApi(Class<T> apiClass, Pair<T, MockInvocationHandler> apiToAdd) {
        services.put(apiClass, apiToAdd.first);
        handlers.put(apiClass, apiToAdd.second);
        return apiToAdd.first;
    }



    public MockInvocationHandler getHandler(Type apiType) {
        return handlers.get(apiType);
    }


    public int getApiCount() {
        return services.size();
    }

    public Set<Type> getApiSet() {
        return services.keySet();
    }

    public static void showConfigScreen(Activity activity) {
        Intent i = new Intent(activity, ConfigurationActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivityForResult(i, CONFIG_REQUEST_CODE);
    }

    public static void onActivityResult(final Activity activity, int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CONFIG_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    new Handler().post(new Runnable() {//fix for nexus 7. why does this work?
                        @Override
                        public void run() {
                            activity.recreate();
                        }
                    });
                }
                break;
        }
    }

}
