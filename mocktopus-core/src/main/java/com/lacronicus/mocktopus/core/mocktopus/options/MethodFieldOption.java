package com.lacronicus.mocktopus.core.mocktopus.options;

/**
 * Created by fdoyle on 8/4/14.
 */
public class MethodFieldOption<T> {
    T value;
    String description;

    public MethodFieldOption(T setting) {
        this.value = setting;
        this.description = setting.toString();
    }

    public MethodFieldOption(String description, T setting) {
        this.value = setting;
        this.description = description;
    }


    public Object getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }


    //rethink equality here. do i only need to care about values, or should descriptions matter too?
    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof MethodFieldOption) {
            MethodFieldOption option = (MethodFieldOption) o;
            if (value != null && description != null) {
                return value.equals(option.value) && description.equals(option.description);
            } else if (value != null) {
                return value.equals(option.value);
            } else if (option.value == null) {
                return true;
            } else return false;
        } else {
            return false;
        }
    }

    public static MethodFieldOption nullOption() {
        return new MethodFieldOption("(null)", null);
    }
}
