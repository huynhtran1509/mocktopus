package com.lacronicus.mocktopus.core.mocktopus.options;

import com.lacronicus.mocktopus.core.mocktopus.Settings;
import com.lacronicus.mocktopus.core.mocktopus.FlattenedOptions;
import com.lacronicus.mocktopus.core.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fdoyle on 7/25/14.
 *
 * "Leaf" as would exist in a tree
 */
public class LeafOptionsNode implements IOptionsNode {
    List<MethodFieldOption> options;
    Method method; // the method this OptionsNode is being created for
    Field field; // the field this is going into
    Type layerType; // the type of the thing this represents


    //field may, in very rare circumstances, be null"
    public LeafOptionsNode(FieldOptionsListBuilder optionsBuilder, Method method, Field field, Type layerType, int depth) {
        options = new ArrayList<MethodFieldOption>();
        this.method = method;
        this.field = field;
        this.layerType = layerType;

        //todo clean this up. could have one method that takes a class
        if (layerType.equals(String.class)) {
            options = optionsBuilder.getStringOptions(field);
        } else if (layerType.equals(Integer.class)) {
            options = optionsBuilder.getIntegerOptions(field, true);
        } else if (layerType.equals(int.class)) {
            options = optionsBuilder.getIntegerOptions(field, false);
        } else if (layerType.equals(Long.class)) {
            options = optionsBuilder.getLongOptions(field, true);
        } else if (layerType.equals(long.class)) {
            options = optionsBuilder.getLongOptions(field, false);
        } else if (layerType.equals(Double.class)) {
            options = optionsBuilder.getDoubleOptions(field, true);
        } else if (layerType.equals(double.class)) {
            options = optionsBuilder.getDoubleOptions(field, false);
        } else if (layerType.equals(Float.class)) {
            options = optionsBuilder.getFloatOptions(field, true);
        } else if (layerType.equals(float.class)) {
            options = optionsBuilder.getFloatOptions(field, false);
        } else if (layerType.equals(Character.class)) {
            options = optionsBuilder.getCharOptions(field, true);
        } else if (layerType.equals(char.class)) {
            options = optionsBuilder.getCharOptions(field, false);
        } else if (layerType.equals(Boolean.class)) {
            options = optionsBuilder.getBooleanOptions(field, true);
        } else if (layerType.equals(boolean.class)) {
            options = optionsBuilder.getBooleanOptions(field, false);
        }
    }

    @Override
    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addField(method, field, layerType, options);
    }

    @Override
    public void addDefaultSettingsTo(Settings toAdd)  {
        toAdd.put(method, field, options.get(0));
    }
}
