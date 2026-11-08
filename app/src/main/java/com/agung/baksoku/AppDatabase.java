package com.agung.baksoku;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Pesanan.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract PesananDao pesananDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "baksoku.db")
                    .allowMainThreadQueries() // data kecil, cukup untuk tugas kuliah
                    .fallbackToDestructiveMigration() // skema berubah (v1 -> v2): riwayat lama dihapus
                    .build();
        }
        return instance;
    }
}
