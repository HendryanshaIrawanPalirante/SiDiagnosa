package com.example.ta_hendryansha.adapter.kelola_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.Admin
import com.example.ta_hendryansha.entity.Gejala

class AdminAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<AdminAdapter.ListViewHolder>() {

    var listAdmin = ArrayList<Admin>()
        set(listAdmin){
            if(listAdmin.size > 0){
                this.listAdmin.clear()
            }
            this.listAdmin.addAll(listAdmin)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun addItem(admin: Admin) {
        this.listAdmin.add(admin)
        notifyItemInserted(this.listAdmin.size - 1)
    }

    fun updateItem(position: Int, admin: Admin) {
        this.listAdmin[position] = admin
        notifyItemChanged(position, admin)
    }

    fun removeItem(position: Int) {
        this.listAdmin.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listAdmin.size)
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvNama)
        val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvJenisKelamin)
        val tvUmur: TextView = itemView.findViewById(R.id.tvUmur)
        val tvNik: TextView = itemView.findViewById(R.id.tvNik)
        val imgDelete: ImageView = itemView.findViewById(R.id.imageDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_admin, parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminAdapter.ListViewHolder, position: Int) {
        val data = listAdmin[position]
        holder.tvName.text = data.namaLengkap
        holder.tvJenisKelamin.text = data.jenisKelamin
        holder.tvUmur.text = data.umur.toString()
        holder.tvNik.text = data.nik.toString()
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.imgDelete.setOnClickListener{
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listAdmin.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(selectedAdmin: Admin?, position: Int?)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedAdmin: Admin?, position: Int?)
    }

}