package com.agung.baksoku;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pesanan")
public class Pesanan {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String ringkasan;
    public int total;
    public String waktu;
    public String tipe;    // "Makan di sini" / "Bungkus"
    public String catatan; // misal: tanpa seledri, sambel dipisah
}
