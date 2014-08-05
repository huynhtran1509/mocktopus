package com.lacronicus.mocktopus.core.mocktopus.parser;
import com.lacronicus.mocktopus.annotation.string.StringDate;
import com.lacronicus.mocktopus.annotation.string.StringFixed;
import com.lacronicus.mocktopus.annotation.string.StringImageUrl;
import com.lacronicus.mocktopus.annotation.string.StringWebpageUrl;
import com.lacronicus.mocktopus.core.mocktopus.options.MethodFieldOption;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParams;
import com.lacronicus.mocktopus.core.mocktopus.params.MocktopusParamsBuilder;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fdoyle on 7/10/14.
 * <p/>
 * some thoughts:
 * I could do the check for field types here, and return a different list based on what it needs
 * but then it'd have to return a list of Objects and not a list of Strings or Integers specifically
 * <p/>
 * maybe some generics wizardry?
 */
public class FieldOptionsListBuilder {

    MocktopusParams params;

    public FieldOptionsListBuilder(MocktopusParams params) {
        this.params = params;
    }

    private FieldOptionsListBuilder() {
        this.params = new MocktopusParamsBuilder().defaultGlobalParams().build();
    }


    public List<MethodFieldOption> getStringOptions(Field field) {
        List<MethodFieldOption> returnList = new ArrayList<MethodFieldOption>();

        //todo sane ordering of annotations? which ones are most important and should be default

        if(field != null) {
            if (field.isAnnotationPresent(StringFixed.class)) {
                StringFixed fixed = field.getAnnotation(StringFixed.class);
                returnList.add(new MethodFieldOption(fixed.value()));
            }
            if (field.isAnnotationPresent(StringDate.class)) {
                String formatString = field.getAnnotation(StringDate.class).value();
                SimpleDateFormat format = new SimpleDateFormat(formatString);
                String dateString = format.format(new Date());
                returnList.add(new MethodFieldOption(dateString));


            }
            if (field.isAnnotationPresent(StringImageUrl.class)) {
                StringImageUrl fixed = field.getAnnotation(StringImageUrl.class);
                returnList.add(new MethodFieldOption(fixed.value()));
            }

            if (field.isAnnotationPresent(StringWebpageUrl.class)) {
                StringWebpageUrl fixed = field.getAnnotation(StringWebpageUrl.class);
                returnList.add(new MethodFieldOption(fixed.value()));
            }
        }

        returnList.addAll(params.getOptionsForType(String.class));
        returnList.add(MethodFieldOption.nullOption());
        return returnList;
    }

    public List<MethodFieldOption> getIntegerOptions(Field field, boolean isObject) {
        List<MethodFieldOption> returnList = new ArrayList<MethodFieldOption>();
        returnList.addAll(params.getOptionsForType(Integer.class));
        if (isObject)
            returnList.add(MethodFieldOption.nullOption());
        return returnList;
    }

    public List<MethodFieldOption> getLongOptions(Field field, boolean isObject) {
        List<MethodFieldOption> returnList = new ArrayList<MethodFieldOption>();
        returnList.addAll(params.getOptionsForType(Long.class));
        if (isObject)
            returnList.add(MethodFieldOption.nullOption());
        return returnList;
    }

    public List<MethodFieldOption> getDoubleOptions(Field field, boolean isObject) {
        List<MethodFieldOption> returnList = new ArrayList<MethodFieldOption>();
        returnList.addAll(params.getOptionsForType(Double.class));
        if (isObject)
            returnList.add(MethodFieldOption.nullOption());
        return returnList;
    }

    public List<MethodFieldOption> getFloatOptions(Field field, boolean isObject) {
        List<MethodFieldOption> returnList = new ArrayList<MethodFieldOption>();
        returnList.addAll(params.getOptionsForType(Float.class));
        if (isObject)
            returnList.add(MethodFieldOption.nullOption());
        return returnList;
    }


    public List<MethodFieldOption> getCharOptions(Field field, boolean isObject) {
        List<MethodFieldOption> returnList = new ArrayList<MethodFieldOption>();
        returnList.addAll(params.getOptionsForType(Character.class));
        if (isObject)
            returnList.add(MethodFieldOption.nullOption());
        return returnList;
    }


    public List<MethodFieldOption> getBooleanOptions(Field field, boolean isObject) {
        List<MethodFieldOption> returnList = new ArrayList<MethodFieldOption>();
        returnList.addAll(params.getOptionsForType(Boolean.class));
        if (isObject)
            returnList.add(MethodFieldOption.nullOption());
        return returnList;
    }



}
