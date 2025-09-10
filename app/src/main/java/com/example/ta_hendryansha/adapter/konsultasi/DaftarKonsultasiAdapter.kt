package com.example.ta_hendryansha.adapter.konsultasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.remote.response.user.DataItemKonsultasi

class DaftarKonsultasiAdapter(val listRiwayat: ArrayList<DataItemKonsultasi>): RecyclerView.Adapter<DaftarKonsultasiAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvName: TextView = itemView.findViewById(R.id.tvNama)
        val tvGender: TextView = itemView.findViewById(R.id.tvGender)
        val tvUmur: TextView = itemView.findViewById(R.id.tvUmur)
        val tvNoBpjs: TextView = itemView.findViewById(R.id.tvNoBpjs)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarKonsultasiAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_konsultasi, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaftarKonsultasiAdapter.ListViewHolder, position: Int) {
        val data = listRiwayat[position]

        holder.tvName.text = data.namaPasien
        holder.tvGender.text = data.jenisKelamin
        holder.tvUmur.text = data.umur.toString()
        holder.tvNoBpjs.text = data.noBpjs.toString()
        holder.tvStatus.text = data.statusKonsultasi
        holder.btnDelete.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return listRiwayat.size
    }
}