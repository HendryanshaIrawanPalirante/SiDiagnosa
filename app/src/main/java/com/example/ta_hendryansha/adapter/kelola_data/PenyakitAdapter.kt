package com.example.ta_hendryansha.adapter.kelola_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.Penyakit

class PenyakitAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<PenyakitAdapter.ListViewHolder>() {

    var listPenyakit = ArrayList<Penyakit>()
        set(listPenyakit){
            if(listPenyakit.size > 0){
                this.listPenyakit.clear()
            }
            this.listPenyakit.addAll(listPenyakit)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun addItem(penyakit: Penyakit) {
        this.listPenyakit.add(penyakit)
        notifyItemInserted(this.listPenyakit.size - 1)
    }

    fun updateItem(position: Int, penyakit: Penyakit) {
        this.listPenyakit[position] = penyakit
        notifyItemChanged(position, penyakit)
    }

    fun removeItem(position: Int) {
        this.listPenyakit.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listPenyakit.size)
    }


    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNamaPenyakit: TextView = itemView.findViewById(R.id.tv_nama_penyakit)
        val tvKodePenyakit: TextView = itemView.findViewById(R.id.tv_kode_penyakit)
        val imgDelete: ImageView = itemView.findViewById(R.id.imageDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PenyakitAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_penyakit, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PenyakitAdapter.ListViewHolder, position: Int) {
        val data = listPenyakit[position]
        holder.tvNamaPenyakit.text = data.namaPenyakit
        holder.tvKodePenyakit.text = data.kodePenyakit
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.imgDelete.setOnClickListener{
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listPenyakit.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(selectedPenyakit: Penyakit?, position: Int?)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedPenyakit: Penyakit?, position: Int?)
    }
}