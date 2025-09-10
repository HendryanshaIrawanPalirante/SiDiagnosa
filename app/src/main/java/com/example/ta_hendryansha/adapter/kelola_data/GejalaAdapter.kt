package com.example.ta_hendryansha.adapter.kelola_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.Gejala

class GejalaAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<GejalaAdapter.ListViewHolder>(){

    var listGejala = ArrayList<Gejala>()
        set(listGejala){
            if(listGejala.size > 0){
                this.listGejala.clear()
            }
            this.listGejala.addAll(listGejala)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun addItem(gejala: Gejala) {
        this.listGejala.add(gejala)
        notifyItemInserted(this.listGejala.size - 1)
    }

    fun updateItem(position: Int, gejala: Gejala) {
        this.listGejala[position] = gejala
        notifyItemChanged(position, gejala)
    }

    fun removeItem(position: Int) {
        this.listGejala.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listGejala.size)
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNamaGejala: TextView = itemView.findViewById(R.id.tv_nama_gejala)
        val tvKodeGejala: TextView = itemView.findViewById(R.id.tv_kode_gejala)
        val imgDelete: ImageView = itemView.findViewById(R.id.imageDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GejalaAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_gejala, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GejalaAdapter.ListViewHolder, position: Int) {
        val data = listGejala[position]
        holder.tvKodeGejala.text = data.kodeGejala
        holder.tvNamaGejala.text = data.namaGejala
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.imgDelete.setOnClickListener{
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listGejala.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(selectedGejala: Gejala?, position: Int?)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedGejala: Gejala?, position: Int?)
    }

}