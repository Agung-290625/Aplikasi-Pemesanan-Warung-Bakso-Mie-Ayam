package com.agung.baksoku;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PesananDao {
    @Insert
    void insert(Pesanan pesanan);

    @Query("SELECT * FROM pesanan ORDER BY id DESC")
    List<Pesanan> semua();
}
