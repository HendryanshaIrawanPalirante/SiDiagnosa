package com.example.ta_hendryansha.adapter.kelola_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.Notifikasi
import com.example.ta_hendryansha.entity.User
import com.example.ta_hendryansha.remote.response.konsultasi.DataItemNotifikasi
import com.example.ta_hendryansha.remote.response.konsultasi.DetailKonsultasiItem
import org.w3c.dom.Text

class NotifikasiAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<NotifikasiAdapter.ListViewHolder>() {

    var listNotifikasi = ArrayList<Notifikasi>()
        set(listNotifikasi){
            if(listNotifikasi.size > 0){
                this.listNotifikasi.clear()
            }
            this.listNotifikasi.addAll(listNotifikasi)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun removeItem(position: Int) {
        this.listNotifikasi.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotifikasi.size)
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvGender)
        val tvUmur: TextView = itemView.findViewById(R.id.tvUmur)
        val tvNoBpjs: TextView = itemView.findViewById(R.id.tvNoBpjs)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val imgDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotifikasiAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_konsultasi, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotifikasiAdapter.ListViewHolder, position: Int) {
        val data = listNotifikasi[position]
        holder.tvNama.text = data.namaPasien
        holder.tvJenisKelamin.text = data.jenisKelamin
        holder.tvUmur.text = data.umur.toString()
        holder.tvNoBpjs.text = data.noBpjs.toString()
        holder.tvStatus.text = data.statusKonsultasi
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.imgDelete.setOnClickListener{
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listNotifikasi.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(selectedNotifikasi: Notifikasi?, position: Int?)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedNotifikasi: Notifikasi?, position: Int?)
    }

}