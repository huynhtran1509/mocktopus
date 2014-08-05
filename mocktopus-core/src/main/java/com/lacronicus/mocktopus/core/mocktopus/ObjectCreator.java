package com.lacronicus.mocktopus.core.mocktopus;

import android.util.Log;

import com.lacronicus.mocktopus.annotation.ListModder;
import com.lacronicus.mocktopus.annotation.modder.IListModder;
import com.lacronicus.mocktopus.core.mocktopus.options.MethodFieldOption;
import com.lacronicus.mocktopus.core.mocktopus.options.Options;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;

/**
 * Created by fdoyle on 7/24/14.
 */
public class ObjectCreator {

    /**
     * this is called recursively to "inflate" the object
     * <p/>
     * this could use some love
     *
     * @param returnType      the type of the object being created
     * @param method          the method this object is being created as a return value for
     * @param f               the field the object is about to be put into, null if it's the top-level object
     * @param currentSettings the settings object being used to create new methods
     */
    public Object createObject(Type returnType, Method method, Field f, Settings currentSettings) {
        log("creating a new object");
        Class<?> returnClass;
        if (returnType instanceof Class) {
            returnClass = (Class<?>) returnType;
        } else {//if(childType instanceof ParameterizedType) {
            returnClass = (Class<?>) ((ParameterizedType) returnType).getRawType();
        } //is there ever going to be something else?
        try {
            if (returnClass.equals(String.class) ||
                    returnClass.equals(Integer.class) ||
                    returnClass.equals(Long.class) ||
                    returnClass.equals(Float.class) ||
                    returnClass.equals(Double.class) ||
                    returnClass.equals(Character.class) ||
                    returnClass.equals(Boolean.class)) {
                MethodFieldOption returnOption = currentSettings.get(method, f);
                return returnOption.getValue();
            } else if (Platform.HAS_RX_JAVA && Observable.class.isAssignableFrom(returnClass)) {
                Type containedClass = ((ParameterizedType) returnType).getActualTypeArguments()[0];
                //return Observable.from(createObject(containedClass, method, null, currentSettings));
                return  currentSettings.getObservableOption(method).createObservableForObject(createObject(containedClass, method, null, currentSettings));
            } else if (Collection.class.isAssignableFrom(returnClass)) {
                List<Object> collection = new ArrayList<Object>();
                Type containedType = ((ParameterizedType) returnType).getActualTypeArguments()[0];
                if (containedType instanceof Class<?> && ((Class<?>) containedType).isAnnotationPresent(ListModder.class)) {
                    Class containedClass = (Class<?>) containedType;
                    ListModder builderAnnotation = (ListModder) containedClass.getAnnotation(ListModder.class);
                    IListModder builder = builderAnnotation.value().newInstance();
                    int count = builder.getCount();
                    for (int i = 0; i != count; i++) {
                        collection.add(createObject(containedType, method, f, currentSettings));
                    }
                    builder.modifyList(collection);
                } else {

                    collection.add(createObject(containedType, method, f, currentSettings));
                    collection.add(createObject(containedType, method, f, currentSettings));
                    collection.add(createObject(containedType, method, f, currentSettings));
                }

                return collection;
            } else { // this is a traditional model object, fill its fields
                Object response = returnClass.newInstance();
                Field[] childFields = returnClass.getDeclaredFields(); // todo add support for super classes here
                for (int i = 0; i != childFields.length; i++) {
                    Field childField = childFields[i];
                    Class fieldType = childField.getType();
                    if (fieldType.equals(String.class) ||
                            fieldType.equals(Integer.class) ||
                            fieldType.equals(Long.class) ||
                            fieldType.equals(Double.class) ||
                            fieldType.equals(Float.class) ||
                            fieldType.equals(Character.class) ||
                            fieldType.equals(Boolean.class)) {
                        childField.set(response, createObject(fieldType, method, childField, currentSettings));
                    } else if (fieldType.equals(int.class)) {
                        childField.setInt(response, (Integer) currentSettings.get(method, childField).getValue());
                    } else if (fieldType.equals(long.class)) {
                        childField.setLong(response, (Long) currentSettings.get(method, childField).getValue());
                    } else if (fieldType.equals(double.class)) {
                        childField.setDouble(response, (Double) currentSettings.get(method, childField).getValue());
                    } else if (fieldType.equals(float.class)) {
                        childField.setFloat(response, (Float) currentSettings.get(method, childField).getValue());
                    } else if (fieldType.equals(char.class)) {
                        childField.setChar(response, (Character) currentSettings.get(method, childField).getValue());
                    } else if (fieldType.equals(boolean.class)) {
                        childField.setBoolean(response, (Boolean) currentSettings.get(method, childField).getValue());
                    } else { // best way to determine child classes? what if it contains an Activity for some awful reason?
                        // may need to explicity state what children to add
                        childField.set(response, createObject(childField.getGenericType(), method, childField, currentSettings));
                    }
                }
                return response;
            }
        } catch (InstantiationException e) {
            log("failed to instantiate");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log("failed to access, are all of your fields public?"); // way around this issue?
            e.printStackTrace();
        }
        return null;
    }


    private static void log(String statement) {
        Log.d(Tag.mainTag + Options.class.getName(), statement);
    }
}
