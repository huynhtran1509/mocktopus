package com.lacronicus.mocktopus.core.mocktopus;

import android.util.Log;

public class Platform {
    public static final boolean HAS_RX_JAVA = hasRxJavaOnClasspath();
    public static final boolean HAS_RETROFIT = hasRetrofitOnClaspath();

    private static boolean hasRxJavaOnClasspath() {
        try {
            Class.forName("rx.Observable");
            Log.d(Platform.class.getSimpleName(),"RxJava found, keeping support");
            return true;
        } catch (ClassNotFoundException ignored) {
            Log.d(Platform.class.getSimpleName(),"RxJava not found, dropping support for rxJava-specific functionality");
        }
        return false;
    }

    private static boolean hasRetrofitOnClaspath() {
        try {
            Class.forName("retrofit.RestAdapter");
            Log.d(Platform.class.getSimpleName(),"Retrofit found, keeping support");
            return true;
        } catch (ClassNotFoundException ignored) {
            Log.d(Platform.class.getSimpleName(),"Retrofit found, dropping support for retrofit-specific functionality");
        }
        return false;
    }
}
