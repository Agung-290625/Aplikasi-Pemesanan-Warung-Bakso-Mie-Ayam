package com.agung.baksoku;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private PrefsManager prefs;
    private EditText etNama;
    private SwitchCompat swTema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        UiUtil.statusBarSesuaiTema(this);

        prefs = new PrefsManager(this);
        etNama = findViewById(R.id.etNama);
        swTema = findViewById(R.id.swTema);
        Button btnSimpan = findViewById(R.id.btnSimpan);
        Button btnKembali = findViewById(R.id.btnKembali);

        UiUtil.terapkanInsets(findViewById(R.id.root), findViewById(R.id.header), null);

        btnKembali.setOnClickListener(v -> finish());
        btnSimpan.setOnClickListener(v -> {
            prefs.simpan(etNama.getText().toString().trim(), swTema.isChecked());
            prefs.terapkanTema();
            Toast.makeText(this, "Siap! Warung sudah hafal kamu \uD83D\uDC4D", Toast.LENGTH_SHORT).show();
        });
    }

    // Nilai tersimpan dibaca di onResume() agar form selalu sesuai isi SharedPreferences
    @Override
    protected void onResume() {
        super.onResume();
        etNama.setText(prefs.getNama());
        swTema.setChecked(prefs.isTemaGelap());
    }
}
