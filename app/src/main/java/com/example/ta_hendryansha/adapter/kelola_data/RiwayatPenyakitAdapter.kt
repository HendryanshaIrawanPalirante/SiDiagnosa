package com.example.ta_hendryansha.adapter.kelola_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.RiwayatPenyakit
import org.w3c.dom.Text

class RiwayatPenyakitAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<RiwayatPenyakitAdapter.ListViewHolder>() {

    var listRiwayat = ArrayList<RiwayatPenyakit>()
        set(listRiwayat){
            if(listRiwayat.size > 0){
                this.listRiwayat.clear()
            }
            this.listRiwayat.addAll(listRiwayat)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvNama)
        val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvGender)
        val tvUmur: TextView = itemView.findViewById(R.id.tvUmur)
        val tvNoBpjs: TextView = itemView.findViewById(R.id.tvNoBpjs)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    fun removeItem(position: Int){
        this.listRiwayat.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listRiwayat.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RiwayatPenyakitAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_konsultasi, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiwayatPenyakitAdapter.ListViewHolder, position: Int) {
        val data = listRiwayat[position]
        holder.tvName.text = data.namaPasien
        holder.tvJenisKelamin.text = data.jenisKelamin
        holder.tvUmur.text = data.umur.toString()
        holder.tvNoBpjs.text = data.noBpjs.toString()
        holder.tvStatus.text = data.statusKonsultasi
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.btnDelete.setOnClickListener {
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listRiwayat.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(selectedRiwayat: RiwayatPenyakit?, position: Int)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedRiwayat: RiwayatPenyakit?, position: Int)
    }

}