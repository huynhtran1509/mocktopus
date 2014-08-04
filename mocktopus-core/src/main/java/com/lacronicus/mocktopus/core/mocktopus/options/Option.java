package com.lacronicus.mocktopus.core.mocktopus.options;

/**
 * Created by fdoyle on 8/4/14.
 */
public class Option{
    Object value;
    String description;

    public Option(Object setting) {
        this.value = setting;
        this.description = setting.toString();
    }

    public Option(String description, Object setting) {
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

    @Override
    public boolean equals(Object o) {
        if(o instanceof Option) {
            Option option = (Option) o;
            return value.equals(option.value) && description.equals(option.description);
        } else {
            return false;
        }
    }

    public static Option nullOption() {
        return new Option("(null)", null);
    }
}
