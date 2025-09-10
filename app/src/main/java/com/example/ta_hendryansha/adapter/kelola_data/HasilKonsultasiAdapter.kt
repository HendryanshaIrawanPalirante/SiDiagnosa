package com.example.ta_hendryansha.adapter.kelola_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.HasilKonulstasi

class HasilKonsultasiAdapter(private val onItemClickCallback: OnItemClickCallback) :RecyclerView.Adapter<HasilKonsultasiAdapter.ListViewHolder>() {

    var listHasilKonsultasi = ArrayList<HasilKonulstasi>()
        set(listHasilKonsultasi){
            if(listHasilKonsultasi.size > 0){
                this.listHasilKonsultasi.clear()
            }
            this.listHasilKonsultasi.addAll(listHasilKonsultasi)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun removeItem(position: Int) {
        this.listHasilKonsultasi.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listHasilKonsultasi.size)
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvGender)
        val tvUmur: TextView = itemView.findViewById(R.id.tvUmur)
        val tvNoBpjs: TextView = itemView.findViewById(R.id.tvNoBpjs)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HasilKonsultasiAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_konsultasi, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HasilKonsultasiAdapter.ListViewHolder, position: Int) {
        val data = listHasilKonsultasi[position]
        holder.tvNama.text = data.namaPasien
        holder.tvJenisKelamin.text = data.jenisKelamin
        holder.tvUmur.text = data.umur.toString()
        holder.tvNoBpjs.text = data.noBpjs.toString()
        holder.tvStatus.text = data.statusKonsultasi
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.btnDelete.setOnClickListener{
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listHasilKonsultasi.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(hasilKonulstasiSelected: HasilKonulstasi?, position: Int?)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedHasilKonulstasi: HasilKonulstasi?, position: Int?)
    }
}