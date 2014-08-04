package com.lacronicus.mocktopus.core.mocktopus.options;

import android.util.Pair;

import com.lacronicus.mocktopus.core.mocktopus.FieldSettings;
import com.lacronicus.mocktopus.core.mocktopus.FlattenedOptions;
import com.lacronicus.mocktopus.core.mocktopus.parser.FieldOptionsListBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fdoyle on 7/25/14.
 */
public class LeafOptionsNode implements IOptionsNode {
    List<Option> options;
    Method method; // the method this OptionsNode is being created for
    Field field; // the field this is going into
    Type layerType; // the type of the thing this represents


    //field may, in very rare circumstances, be null"
    public LeafOptionsNode(FieldOptionsListBuilder optionsBuilder, Method method, Field field, Type layerType, int depth) {
        options = new ArrayList<Option>();
        this.method = method;
        this.field = field;
        this.layerType = layerType;

        if (layerType.equals(String.class)) {
            options = optionsBuilder.getOptionsForStringField(field);
        } else if (layerType.equals(Integer.class)) {
            options = optionsBuilder.getOptionsforIntegerField(field, true);
        } else if (layerType.equals(int.class)) {
            options = optionsBuilder.getOptionsforIntegerField(field, false);
        } else if (layerType.equals(Long.class)) {
            options = optionsBuilder.getOptionsforLongField(field, true);
        } else if (layerType.equals(long.class)) {
            options = optionsBuilder.getOptionsforLongField(field, false);
        } else if (layerType.equals(Double.class)) {
            options = optionsBuilder.getOptionsforDoubleField(field, true);
        } else if (layerType.equals(double.class)) {
            options = optionsBuilder.getOptionsforDoubleField(field, false);
        } else if (layerType.equals(Float.class)) {
            options = optionsBuilder.getOptionsforFloatField(field, true);
        } else if (layerType.equals(float.class)) {
            options = optionsBuilder.getOptionsforFloatField(field, false);
        } else if (layerType.equals(Character.class)) {
            options = optionsBuilder.getOptionsforCharField(field, true);
        } else if (layerType.equals(char.class)) {
            options = optionsBuilder.getOptionsforCharField(field, false);
        } else if (layerType.equals(Boolean.class)) {
            options = optionsBuilder.getOptionsforBooleanField(field, true);
        } else if (layerType.equals(boolean.class)) {
            options = optionsBuilder.getOptionsforBooleanField(field, false);
        }
    }

    @Override
    public void addToFlattenedOptions(FlattenedOptions flattenedOptions) {
        flattenedOptions.addField(method, field, layerType, options);
    }

    @Override
    public void addDefaultSettingsTo(FieldSettings toAdd) {
        toAdd.put(new Pair<Method, Field>(method, field), options.get(0));
    }
}
