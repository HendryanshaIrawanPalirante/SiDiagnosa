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
import com.example.ta_hendryansha.entity.RuleCf

class RuleCfAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<RuleCfAdapter.ListViewHolder>() {

    var listRuleCf = ArrayList<RuleCf>()
        set(listGejala){
            if(listGejala.size > 0){
                this.listRuleCf.clear()
            }
            this.listRuleCf.addAll(listGejala)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun addItem(rule: RuleCf) {
        this.listRuleCf.add(rule)
        notifyItemInserted(this.listRuleCf.size - 1)
    }

    fun updateItem(position: Int, rule: RuleCf) {
        this.listRuleCf[position] = rule
        notifyItemChanged(position, rule)
    }

    fun removeItem(position: Int) {
        this.listRuleCf.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listRuleCf.size)
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvKodePenyakit: TextView = itemView.findViewById(R.id.tvKodePenyakit)
        val tvKodeGejala: TextView = itemView.findViewById(R.id.tvKodeGejala)
        val tvNilaiCf: TextView = itemView.findViewById(R.id.tvNilaiCf)
        val imgDelete: ImageView = itemView.findViewById(R.id.btnRuleDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RuleCfAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_rule_cf, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RuleCfAdapter.ListViewHolder, position: Int) {
        val data = listRuleCf[position]
        holder.tvKodeGejala.text = data.kodeGejala
        holder.tvKodePenyakit.text = data.kodePenyakit
        holder.tvNilaiCf.text = data.nilaiCf.toString()
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.imgDelete.setOnClickListener{
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listRuleCf.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(selectedRuleCf: RuleCf?, position: Int?)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedRuleCf: RuleCf?, position: Int?)
    }

}