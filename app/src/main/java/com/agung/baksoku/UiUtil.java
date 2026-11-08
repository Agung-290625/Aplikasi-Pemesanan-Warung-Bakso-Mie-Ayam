package com.agung.baksoku;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class UiUtil {
    /** Memberi ruang untuk status bar (atas) dan navigation bar (bawah) agar layout tidak tertutup. */
    public static void terapkanInsets(View root, View atas, View bawah) {
        final int pt = atas.getPaddingTop();
        final int pb = bawah != null ? bawah.getPaddingBottom() : 0;
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            atas.setPadding(atas.getPaddingLeft(), pt + bars.top, atas.getPaddingRight(), atas.getPaddingBottom());
            if (bawah != null) {
                bawah.setPadding(bawah.getPaddingLeft(), bawah.getPaddingTop(), bawah.getPaddingRight(), pb + bars.bottom);
            }
            return insets;
        });
    }

    /** Ikon status bar gelap di tema terang, terang di tema gelap. */
    public static void statusBarSesuaiTema(Activity a) {
        boolean malam = (a.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
        WindowInsetsControllerCompat c = new WindowInsetsControllerCompat(a.getWindow(), a.getWindow().getDecorView());
        c.setAppearanceLightStatusBars(!malam);
        c.setAppearanceLightNavigationBars(!malam);
    }
}
