package com.agung.baksoku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.VH> {

    public interface OnJumlahBerubah {
        void onBerubah();
    }

    private final List<MenuMakanan> data;
    private final OnJumlahBerubah listener;

    public MenuAdapter(List<MenuMakanan> data, OnJumlahBerubah listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        MenuMakanan m = data.get(position);
        h.ivFoto.setImageResource(m.gambar);
        // sedikit miring bergantian, seperti foto yang ditempel di papan menu
        h.ivFoto.setRotation(position % 2 == 0 ? -2f : 2f);
        h.tvNama.setText(m.nama);
        h.tvDeskripsi.setText(m.deskripsi);
        h.tvHarga.setText(MenuMakanan.rupiah(m.harga));
        h.tvJumlah.setText(String.valueOf(m.jumlah));

        if (m.tag == null || m.tag.isEmpty()) {
            h.tvTag.setVisibility(View.GONE);
        } else {
            h.tvTag.setVisibility(View.VISIBLE);
            h.tvTag.setText(m.tag);
        }

        h.btnTambah.setOnClickListener(v -> {
            m.jumlah++;
            notifyItemChanged(h.getBindingAdapterPosition());
            listener.onBerubah();
        });
        h.btnKurang.setOnClickListener(v -> {
            if (m.jumlah > 0) {
                m.jumlah--;
                notifyItemChanged(h.getBindingAdapterPosition());
                listener.onBerubah();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvTag, tvNama, tvDeskripsi, tvHarga, tvJumlah;
        Button btnTambah, btnKurang;

        VH(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
            btnTambah = itemView.findViewById(R.id.btnTambah);
            btnKurang = itemView.findViewById(R.id.btnKurang);
        }
    }
}
