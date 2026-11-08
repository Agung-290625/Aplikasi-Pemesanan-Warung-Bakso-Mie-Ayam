package com.agung.baksoku;

import android.app.Application;

public class BaksoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Tema gelap dibaca dari SharedPreferences setiap aplikasi dijalankan
        new PrefsManager(this).terapkanTema();
    }
}
