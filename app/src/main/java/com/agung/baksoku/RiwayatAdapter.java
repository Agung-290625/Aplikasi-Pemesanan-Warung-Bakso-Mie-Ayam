package com.agung.baksoku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.VH> {

    private final List<Pesanan> data;

    public RiwayatAdapter(List<Pesanan> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Pesanan p = data.get(position);
        h.tvNomor.setText("NOTA #" + p.id);
        h.tvWaktu.setText(p.waktu);
        h.tvRingkasan.setText(p.ringkasan);
        h.tvTotal.setText(MenuMakanan.rupiah(p.total));

        String info = p.tipe == null ? "" : p.tipe;
        if (p.catatan != null && !p.catatan.isEmpty()) {
            info += (info.isEmpty() ? "" : " · ") + p.catatan;
        }
        h.tvInfo.setText(info);
        h.tvInfo.setVisibility(info.isEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNomor, tvWaktu, tvRingkasan, tvInfo, tvTotal;

        VH(@NonNull View itemView) {
            super(itemView);
            tvNomor = itemView.findViewById(R.id.tvNomor);
            tvWaktu = itemView.findViewById(R.id.tvWaktu);
            tvRingkasan = itemView.findViewById(R.id.tvRingkasan);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            tvTotal = itemView.findViewById(R.id.tvTotalRiwayat);
        }
    }
}
