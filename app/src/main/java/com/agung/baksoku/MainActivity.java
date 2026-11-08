package com.agung.baksoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private PrefsManager prefs;
    private TextView tvHalo, tvSapaan, tvTotal;
    private Button btnPesan;
    private MenuAdapter adapter;
    private final List<MenuMakanan> daftar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UiUtil.statusBarSesuaiTema(this);

        prefs = new PrefsManager(this);
        tvHalo = findViewById(R.id.tvHalo);
        tvSapaan = findViewById(R.id.tvSapaan);
        tvTotal = findViewById(R.id.tvTotal);
        btnPesan = findViewById(R.id.btnPesan);
        RecyclerView rv = findViewById(R.id.rvMenu);
        Button btnRiwayat = findViewById(R.id.btnRiwayat);
        Button btnPengaturan = findViewById(R.id.btnPengaturan);

        UiUtil.terapkanInsets(findViewById(R.id.root), findViewById(R.id.header), findViewById(R.id.bottomBar));

        daftar.add(new MenuMakanan("Bakso Urat", "Bakso urat kuah gurih dengan tahu", 15000, R.drawable.menu_bakso_urat, "ANDALAN WARUNG"));
        daftar.add(new MenuMakanan("Bakso Telur", "Bakso isi telur, kuah bening hangat", 17000, R.drawable.menu_bakso_telur, ""));
        daftar.add(new MenuMakanan("Bakso Campur", "Bakso, pangsit, tahu, dan kerupuk pangsit", 18000, R.drawable.menu_bakso_campur, "ISI KOMPLIT"));
        daftar.add(new MenuMakanan("Mie Ayam", "Mie ayam dengan sawi dan sambal", 13000, R.drawable.menu_mie_ayam, ""));
        daftar.add(new MenuMakanan("Mie Ayam Bakso", "Mie ayam komplit, bakso dan kuah terpisah", 18000, R.drawable.menu_mie_ayam_bakso, ""));
        daftar.add(new MenuMakanan("Es Teh Manis", "Teh dingin segar", 4000, R.drawable.menu_es_teh, ""));
        daftar.add(new MenuMakanan("Es Jeruk", "Jeruk peras dingin menyegarkan", 6000, R.drawable.menu_es_jeruk, ""));

        adapter = new MenuAdapter(daftar, this::perbaruiTotal);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        btnPesan.setOnClickListener(v -> pesan());
        btnRiwayat.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        btnPengaturan.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        perbaruiTotal();
    }

    // Dibaca ulang setiap layar kembali tampil, supaya nama panggilan selalu terbaru
    @Override
    protected void onResume() {
        super.onResume();
        String nama = prefs.getNama();
        tvHalo.setText("Halo, " + (nama.isEmpty() ? "Kak" : nama) + "!");
        tvSapaan.setText(sapaanWaktu());
    }

    private String sapaanWaktu() {
        int jam = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (jam < 10) return "Pagi-pagi enaknya yang hangat berkuah. Sarapan bakso?";
        if (jam < 15) return "Jam makan siang! Kuah masih panas, mie masih kenyal.";
        if (jam < 18) return "Sore-sore begini, semangkuk bakso pas banget.";
        return "Lapar malam-malam? Dapur kami masih nyala.";
    }

    private int hitungTotal() {
        int total = 0;
        for (MenuMakanan m : daftar) total += m.harga * m.jumlah;
        return total;
    }

    private String ringkasan() {
        StringBuilder sb = new StringBuilder();
        for (MenuMakanan m : daftar) {
            if (m.jumlah > 0) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(m.jumlah).append("x ").append(m.nama);
            }
        }
        return sb.toString();
    }

    private void perbaruiTotal() {
        int item = 0;
        for (MenuMakanan m : daftar) item += m.jumlah;
        tvTotal.setText(MenuMakanan.rupiah(hitungTotal()));
        btnPesan.setText(item > 0 ? "Pesan (" + item + ")" : "Pesan");
    }

    private void pesan() {
        if (hitungTotal() == 0) {
            Toast.makeText(this, "Pilih menunya dulu, Kak", Toast.LENGTH_SHORT).show();
            return;
        }
        View isi = getLayoutInflater().inflate(R.layout.dialog_pesan, null);
        TextView tvRingkas = isi.findViewById(R.id.tvRingkasDialog);
        ChipGroup cgTipe = isi.findViewById(R.id.cgTipe);
        EditText etCatatan = isi.findViewById(R.id.etCatatan);
        tvRingkas.setText(ringkasan() + "\n" + MenuMakanan.rupiah(hitungTotal()));

        new MaterialAlertDialogBuilder(this)
                .setTitle("Satu langkah lagi \uD83C\uDF5C")
                .setView(isi)
                .setPositiveButton("Kirim ke dapur", (d, w) -> {
                    String tipe = cgTipe.getCheckedChipId() == R.id.chipBungkus ? "Bungkus" : "Makan di sini";
                    simpanPesanan(tipe, etCatatan.getText().toString().trim());
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void simpanPesanan(String tipe, String catatan) {
        Pesanan p = new Pesanan();
        p.ringkasan = ringkasan();
        p.total = hitungTotal();
        p.tipe = tipe;
        p.catatan = catatan;
        p.waktu = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("id", "ID")).format(new Date());
        AppDatabase.getInstance(this).pesananDao().insert(p);

        for (MenuMakanan m : daftar) m.jumlah = 0;
        adapter.notifyDataSetChanged();
        perbaruiTotal();
        Toast.makeText(this, "Pesanan masuk dapur! Ditunggu ya, Kak \uD83C\uDF5C", Toast.LENGTH_LONG).show();
    }
}
