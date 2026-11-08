package com.agung.baksoku;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        UiUtil.statusBarSesuaiTema(this);

        UiUtil.terapkanInsets(findViewById(R.id.root), findViewById(R.id.header), null);
        Button btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(v -> finish());

        RecyclerView rv = findViewById(R.id.rvRiwayat);
        TextView tvKosong = findViewById(R.id.tvKosong);

        List<Pesanan> data = AppDatabase.getInstance(this).pesananDao().semua();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RiwayatAdapter(data));
        tvKosong.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
