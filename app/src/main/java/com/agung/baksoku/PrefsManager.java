package com.agung.baksoku;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Pembungkus SharedPreferences (file: shared_prefs/bakso_prefs.xml, MODE_PRIVATE).
 * Hanya menyimpan data kecil berbentuk key-value: nama panggilan dan tema gelap.
 */
public class PrefsManager {
    private static final String PREF_NAME = "bakso_prefs";
    private static final String KEY_NAMA = "nama_panggilan";
    private static final String KEY_TEMA = "tema_gelap";

    private final SharedPreferences prefs;

    public PrefsManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // apply(): tulis ke disk di thread latar, UI tidak menunggu
    public void simpan(String nama, boolean temaGelap) {
        prefs.edit()
                .putString(KEY_NAMA, nama)
                .putBoolean(KEY_TEMA, temaGelap)
                .apply();
    }

    public String getNama() {
        return prefs.getString(KEY_NAMA, "");
    }

    public boolean isTemaGelap() {
        return prefs.getBoolean(KEY_TEMA, false);
    }

    public void terapkanTema() {
        AppCompatDelegate.setDefaultNightMode(isTemaGelap()
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);
    }
}
