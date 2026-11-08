package com.agung.baksoku;

import java.util.Locale;

public class MenuMakanan {
    public final String nama;
    public final String deskripsi;
    public final int harga;
    public final int gambar; // resource drawable
    public final String tag; // stempel kecil, boleh kosong
    public int jumlah = 0;

    public MenuMakanan(String nama, String deskripsi, int harga, int gambar, String tag) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.gambar = gambar;
        this.tag = tag;
    }

    public static String rupiah(int nilai) {
        return String.format(new Locale("id", "ID"), "Rp %,d", nilai);
    }
}
