package com.lacronicus.mocktopus.core.mocktopus.parser;
import com.lacronicus.mocktopus.annotation.string.StringDate;
import com.lacronicus.mocktopus.annotation.string.StringFixed;
import com.lacronicus.mocktopus.annotation.string.StringImageUrl;
import com.lacronicus.mocktopus.annotation.string.StringWebpageUrl;
import com.lacronicus.mocktopus.core.mocktopus.options.Option;
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


    public List<Option> getOptionsForStringField(Field field) {
        List<Option> returnList = new ArrayList<Option>();

        //todo sane ordering of annotations? which ones are most important and should be default

        if(field != null) {
            if (field.isAnnotationPresent(StringFixed.class)) {
                StringFixed fixed = field.getAnnotation(StringFixed.class);
                returnList.add(new Option(fixed.value()));
            }
            if (field.isAnnotationPresent(StringDate.class)) {
                String formatString = field.getAnnotation(StringDate.class).value();
                SimpleDateFormat format = new SimpleDateFormat(formatString);
                String dateString = format.format(new Date());
                returnList.add(new Option(dateString));


            }
            if (field.isAnnotationPresent(StringImageUrl.class)) {
                StringImageUrl fixed = field.getAnnotation(StringImageUrl.class);
                returnList.add(new Option(fixed.value()));
            }

            if (field.isAnnotationPresent(StringWebpageUrl.class)) {
                StringWebpageUrl fixed = field.getAnnotation(StringWebpageUrl.class);
                returnList.add(new Option(fixed.value()));
            }
        }

        returnList.addAll(params.getOptionsForType(String.class));
        returnList.add(Option.nullOption());
        return returnList;
    }

    public List<Option> getOptionsforIntegerField(Field field, boolean isObject) {
        List<Option> returnList = new ArrayList<Option>();
        returnList.addAll(params.getOptionsForType(Integer.class));
        if (isObject)
            returnList.add(Option.nullOption());
        return returnList;
    }

    public List<Option> getOptionsforLongField(Field field, boolean isObject) {
        List<Option> returnList = new ArrayList<Option>();
        returnList.addAll(params.getOptionsForType(Long.class));
        if (isObject)
            returnList.add(Option.nullOption());
        return returnList;
    }

    public List<Option> getOptionsforDoubleField(Field field, boolean isObject) {
        List<Option> returnList = new ArrayList<Option>();
        returnList.addAll(params.getOptionsForType(Double.class));
        if (isObject)
            returnList.add(Option.nullOption());
        return returnList;
    }

    public List<Option> getOptionsforFloatField(Field field, boolean isObject) {
        List<Option> returnList = new ArrayList<Option>();
        returnList.addAll(params.getOptionsForType(Float.class));
        if (isObject)
            returnList.add(Option.nullOption());
        return returnList;
    }


    public List<Option> getOptionsforCharField(Field field, boolean isObject) {
        List<Option> returnList = new ArrayList<Option>();
        returnList.addAll(params.getOptionsForType(Character.class));
        if (isObject)
            returnList.add(Option.nullOption());
        return returnList;
    }


    public List<Option> getOptionsforBooleanField(Field field, boolean isObject) {
        List<Option> returnList = new ArrayList<Option>();
        returnList.addAll(params.getOptionsForType(Boolean.class));
        if (isObject)
            returnList.add(Option.nullOption());
        return returnList;
    }

}
