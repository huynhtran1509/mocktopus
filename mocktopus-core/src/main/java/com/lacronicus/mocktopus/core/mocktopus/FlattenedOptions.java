package com.lacronicus.mocktopus.core.mocktopus;

import com.lacronicus.mocktopus.core.mocktopus.options.MethodFieldOption;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fdoyle on 7/16/14.
 * represents a flat list of options (for UI purposes) rather than the tree structure Options usually takes
 *
 * todo I really don't like this, feels really brittle. there's no inherent order to it, just however stuff was put into it.
 * of course, that's how sorting trees works, so maybe it's not so bad.
 */
public class FlattenedOptions {
    public List<FlatOptionsItem> itemList;

    public FlattenedOptions() {
        itemList = new ArrayList<FlatOptionsItem>();
    }


    public void addMethod(Method m, String endpoint) {
        itemList.add(new FlatOptionsItem(new MethodItem(m, endpoint)));
    }

    public void addChildObject(Class clazz) {
        itemList.add(new FlatOptionsItem(new ChildObjectItem(clazz)));
    }

    public void addField(Method m, Field f, Type layerType, List<MethodFieldOption> options) {
        itemList.add(new FlatOptionsItem(new MethodFieldItem(m, f, layerType, options)));
    }

    public void addCollection(Type type, Type parameterType) {
        itemList.add(new FlatOptionsItem(new CollectionObjectItem(type, parameterType)));
    }

    public void addObservable(Type type, Type parameterType) {
        itemList.add(new FlatOptionsItem(new ObservableObjectItem(type, parameterType)));
    }


    public class FlatOptionsItem {
        public static final int TYPE_INVALID = -1;
        public static final int TYPE_METHOD = 0;
        public static final int TYPE_CLASS = 1;
        public static final int TYPE_FIELD = 2;
        public static final int TYPE_COLLECTION = 3;
        public static final int TYPE_OBSERVABLE = 4;


        public MethodItem methodItem;
        public ChildObjectItem childObjectItem;
        public MethodFieldItem methodFieldItem;
        public CollectionObjectItem collectionObjectItem;
        public ObservableObjectItem observableObjectItem;

        public FlatOptionsItem(MethodItem item) {
            this.methodItem = item;
        }

        public FlatOptionsItem(MethodFieldItem item) {
            this.methodFieldItem = item;
        }

        public FlatOptionsItem(ChildObjectItem item) {
            this.childObjectItem = item;
        }

        public FlatOptionsItem(CollectionObjectItem item) {
            this.collectionObjectItem = item;
        }
        public FlatOptionsItem(ObservableObjectItem item) {
            this.observableObjectItem = item;
        }

        public int getType() {
            if (methodItem != null) {
                return TYPE_METHOD;
            } else if (childObjectItem != null) {
                return TYPE_CLASS;
            } else if (methodFieldItem != null) {
                return TYPE_FIELD;
            } else if (collectionObjectItem != null) {
                return TYPE_COLLECTION;
            } else if (observableObjectItem != null) {
                return TYPE_OBSERVABLE;
            } else {
                return TYPE_INVALID;
            }
        }

        public String getString() {
            switch (getType()) {
                case TYPE_METHOD:
                    return methodItem.getString();
                case TYPE_CLASS:
                    return childObjectItem.getString();
                case TYPE_FIELD:
                    return methodFieldItem.getString();
                case TYPE_COLLECTION:
                    return collectionObjectItem.getString();
                case TYPE_OBSERVABLE:
                    return observableObjectItem.getString();
                default:
                    return "invalid";
            }
        }

    }

    //represents a method header
    public class MethodItem {
        public Method method;
        public String endpoint;

        public MethodItem(Method method, String endpoint) {
            this.method = method;
            this.endpoint = endpoint;
        }

        public String getString() {
            return "method\n name: " + method.getName() + "\n endpoint: " + endpoint;
        }
    }

    //represents a configurable field
    public class MethodFieldItem {
        public Method method;
        public Field field;
        public Type type;
        public List<MethodFieldOption> fieldOptions;

        //any reason options aren't just created here?
        public MethodFieldItem(Method m, Field f,Type t, List<MethodFieldOption> options) {
            this.method = m;
            this.field = f;
            this.type = t;
            this.fieldOptions = options;
        }

        public String getString() {
            Class clazz = (Class<?>) type;
            if(field != null) {
                return "        " + clazz.getSimpleName() + " " + field.getName();
            } else {
                return "        " + clazz.getSimpleName() + " no field name";
            }
        }
    }

    //represents a child object header
    public class ChildObjectItem {
        public Class clazz;

        public ChildObjectItem(Class clazz) {
            this.clazz = clazz;
        }

        public String getString() {
            return "    class | " + clazz.getSimpleName();
        }
    }

    //represents a collection
    public class CollectionObjectItem {
        public Type clazz;
        public Type genericClass;

        public CollectionObjectItem(Type clazz, Type genericClass) {
            this.clazz = clazz;
            this.genericClass = genericClass;
        }

        public String getString() {
            return "    collection  | " + clazz.toString();
        }
    }

    //represents a collection
    public class ObservableObjectItem {
        public Type clazz;
        public Type genericClass;

        public ObservableObjectItem(Type clazz, Type genericClass) {
            this.clazz = clazz;
            this.genericClass = genericClass;
        }

        public String getString() {
            return "    observable  | " + clazz.toString();
        }
    }
}
