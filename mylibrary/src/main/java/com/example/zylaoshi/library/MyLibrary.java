package com.example.zylaoshi.library;

import android.content.Context;

/**
 * Created by zz on 2017/11/28.
 */

public class MyLibrary {
    private static Context application;
    private static boolean debug;

    public static void init(Context app){
        setApplication(app);
        setDebug(true);
    }

    public static void init(Context app, boolean debug){
        setApplication(app);
        setDebug(debug);
    }

    public static Context getApplication() {
        return application;
    }

    private static void setApplication(Context application) {
        MyLibrary.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        MyLibrary.debug = debug;
    }
}
