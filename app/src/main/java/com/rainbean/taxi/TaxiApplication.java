package com.rainbean.taxi;

import android.app.Application;

public class TaxiApplication extends Application {

    private static TaxiApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static TaxiApplication getInstance() {
        return instance;
    }
}
