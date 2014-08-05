package com.lacronicus.mocktopus.core.mocktopus.options;

import android.util.Log;

import com.lacronicus.mocktopus.core.mocktopus.Platform;
import com.lacronicus.mocktopus.core.mocktopus.Settings;
import com.lacronicus.mocktopus.core.mocktopus.FlattenedOptions;
import com.lacronicus.mocktopus.core.mocktopus.Tag;
import com.lacronicus.mocktopus.core.mocktopus.options.method.MethodOption;
import com.lacronicus.mocktopus.core.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.POST;
import retrofit.http.PUT;
import rx.Observable;

/**
 * Created by fdoyle on 7/10/14.
 * Represents the options for entire interface
 */
public class Options {
    enum LogLevel {
        FULL,
        NONE
    }

    Map<Method, IOptionsNode> methodResponseOptions;
    Map<Method, List<MethodOption>> methodOptions;
    FieldOptionsListBuilder fieldOptionsListBuilder;

    public Options(FieldOptionsListBuilder fieldOptionsListBuilder, Class classToBuild) {
        methodResponseOptions = new HashMap<Method, IOptionsNode>();
        methodOptions = new HashMap<Method, List<MethodOption>>();
        this.fieldOptionsListBuilder = fieldOptionsListBuilder;

        Method[] methods = classToBuild.getMethods();
        for (int i = 0; i != methods.length; i++) {

            Method method = methods[i];
            log("creating new base OptionsNode for method " + method.getName());
            methodOptions.put(method, fieldOptionsListBuilder.getMethodOptions());

            Class returnClass = method.getReturnType();
            if (Observable.class.isAssignableFrom(returnClass)) {
                log("return type is observable: " + returnClass.getSimpleName());
                ParameterizedType methodReturnType = (ParameterizedType) method.getGenericReturnType(); //this works for things that are List<Object>
                Type observableType = methodReturnType.getActualTypeArguments()[0];//learn what's going on here

                methodResponseOptions.put(method, new ObservableOptionsNode(fieldOptionsListBuilder, method, methodReturnType, observableType, 0));


            } else if (Collection.class.isAssignableFrom(returnClass)) { // todo might be a List<Object> or might be something that extends List<Object>
                log("return type is collection: " + returnClass.getSimpleName());

                ParameterizedType methodReturnType = (ParameterizedType) method.getGenericReturnType(); //this works for things that are List<Object>
                methodResponseOptions.put(method, new CollectionOptionsNode(fieldOptionsListBuilder, method, null, methodReturnType, 0));
            } else {
                methodResponseOptions.put(method, new ModelOptionsNode(fieldOptionsListBuilder, method, returnClass, 0));
            }
        }
    }

    public FlattenedOptions flatten() {
        FlattenedOptions flattenedOptions = new FlattenedOptions();
        Set<Method> methodSet = methodResponseOptions.keySet();
        for (Method method : methodSet) {
            //add method to flattenedOptions
            String endpoint = "";
            if(Platform.HAS_RETROFIT) {
                if (method.getAnnotation(GET.class) != null)
                    endpoint += method.getAnnotation(GET.class).value();
                if (method.getAnnotation(POST.class) != null)
                    endpoint += method.getAnnotation(POST.class).value();
                if (method.getAnnotation(PUT.class) != null)
                    endpoint += method.getAnnotation(PUT.class).value();
                if (method.getAnnotation(DELETE.class) != null)
                    endpoint += method.getAnnotation(DELETE.class).value();
                if (method.getAnnotation(HEAD.class) != null)
                    endpoint += method.getAnnotation(HEAD.class).value();
            }

            flattenedOptions.addMethod(method, endpoint, methodOptions.get(method));
            //add fields to flattenedOptions
            methodResponseOptions.get(method).addToFlattenedOptions(flattenedOptions);
        }

        log("done flattening, contains " + flattenedOptions.itemList.size() + " items");
        return flattenedOptions;
    }


    public Settings getDefaultFieldSettings() {
        Settings settings = new Settings();
        for(Method method : methodOptions.keySet()) {
            settings.putMethodOption(method, methodOptions.get(method).get(0));
        }
        for (IOptionsNode node : methodResponseOptions.values()) {
            node.addDefaultSettingsTo(settings);
        }
        return settings;
    }


    private static void log(String statement) {
            Log.d(Tag.mainTag + Options.class.getName(), statement);
    }


}
