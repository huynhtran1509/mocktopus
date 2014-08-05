package com.lacronicus.mocktopus.core.mocktopus;

public class Platform {

    static final boolean HAS_RX_JAVA = hasRxJavaOnClasspath();
    static final boolean HAS_RETROFIT = hasRetrofitOnClaspath();


    private static boolean hasRxJavaOnClasspath() {
        try {
            Class.forName("rx.Observable");
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }

    private static boolean hasRetrofitOnClaspath() {
        try {
            Class.forName("retrofit.RestAdapter");
            return true;
        } catch (ClassNotFoundException ignored) {
        }
        return false;
    }
}
