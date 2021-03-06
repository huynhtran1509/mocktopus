package com.lacronicus.mocktopus.core.mocktopus.options;

import com.lacronicus.mocktopus.core.mocktopus.FieldSettings;
import com.lacronicus.mocktopus.core.mocktopus.FlattenedOptions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by fdoyle on 7/16/14.
 * //todo universal OptionsNode?
 * //because what if you have an object that extends List<Whatever> that also does other stuff?
 */
public class CollectionOptionsNode implements IOptionsNode {

    IOptionsNode node; // represents the children of this collection. may be a model, another collection, or a leaf
    Type containerType;
    Type parameterType;

    public CollectionOptionsNode(Method m, Field f, Type myType, int depth) {
        Type childType = ((ParameterizedType) myType).getActualTypeArguments()[0];
        Class<?> childClass;
        if (childType instanceof Class) {
            childClass = (Class<?>) childType;
        } else {//if(childType instanceof ParameterizedType) {
            childClass = (Class<?>) ((ParameterizedType) childType).getRawType();
        }

        //what if this contains "leaf" objects
        if (Collection.class.isAssignableFrom(childClass)) {
            node = new CollectionOptionsNode(m, f, childType, depth + 1);
        } else if (childType.equals(String.class)) {
            node = new LeafOptionsNode(m, f, childType, depth + 1);
        } else if (childType.equals(Integer.class)) {
            node = new LeafOptionsNode(m, f, childType, depth + 1);
        } else if (childType.equals(Long.class)) {
            node = new LeafOptionsNode(m, f, childType, depth + 1);
        } else if (childType.equals(Float.class)) {
            node = new LeafOptionsNode(m, f, childType, depth + 1);
        } else if (childType.equals(Double.class)) {
            node = new LeafOptionsNode(m, f, childType, depth + 1);
        } else if (childType.equals(Character.class)) {
            node = new LeafOptionsNode(m, f, childType, depth + 1);
        } else if (childType.equals(Boolean.class)) {
            node = new LeafOptionsNode(m, f, childType, depth + 1);
        } else {
            //assume that it contains plain objects
            node = new ModelOptionsNode(m, (Class<?>) childType, depth + 1);//do this if this represents a collection of plain objects
        }
        this.parameterType = childType;
        this.containerType = myType;
    }


    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addCollection(containerType, parameterType);
        node.addToFlattenedOptions(flattenedOptions);
    }

    public void addDefaultSettingsTo(FieldSettings toAdd) {
        node.addDefaultSettingsTo(toAdd);
    }

}
