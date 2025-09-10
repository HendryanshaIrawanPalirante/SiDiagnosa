package com.example.ta_hendryansha.adapter.konsultasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.GejalaKonsultasi


class DetailGejalaAdapter(val listDetailKonsultasi: ArrayList<GejalaKonsultasi>): RecyclerView.Adapter<DetailGejalaAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val namaGejala = itemView.findViewById<TextView>(R.id.tv_nama_gejala)
        val deskripsi = itemView.findViewById<TextView>(R.id.tv_deskripsi)
        val waktu = itemView.findViewById<TextView>(R.id.tv_waktu)
        val intensitas = itemView.findViewById<TextView>(R.id.tv_intensitas)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailGejalaAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_gejala_konsultasi, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailGejalaAdapter.ListViewHolder, position: Int) {
        val data = listDetailKonsultasi[position]
        holder.namaGejala.text = data.namaGejala
        holder.deskripsi.text = data.deskripsi
        holder.waktu.text = data.waktu
        holder.intensitas.text = data.intensitas
    }

    override fun getItemCount(): Int {
        return listDetailKonsultasi.size
    }
}