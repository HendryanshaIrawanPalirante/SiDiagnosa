package com.example.ta_hendryansha.adapter.kelola_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.Penyakit
import com.example.ta_hendryansha.entity.RuleCf
import com.example.ta_hendryansha.entity.RuleFc

class RuleFcAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<RuleFcAdapter.ListViewHolder>() {

    var listRuleFc = ArrayList<RuleFc>()
        set(listRuleFc){
            if(listRuleFc.size > 0){
                this.listRuleFc.clear()
            }
            this.listRuleFc.addAll(listRuleFc)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun addItem(rule: RuleFc) {
        this.listRuleFc.add(rule)
        notifyItemInserted(this.listRuleFc.size - 1)
    }

    fun updateItem(position: Int, rule: RuleFc) {
        this.listRuleFc[position] = rule
        notifyItemChanged(position, rule)
    }

    fun removeItem(position: Int) {
        this.listRuleFc.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listRuleFc.size)
    }

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val tvIdKeputusan: TextView = itemView.findViewById(R.id.tvIdKeputusan)
        val tvYa: TextView = itemView.findViewById(R.id.tvYa)
        val tvTidak: TextView = itemView.findViewById(R.id.tvTidak)
        val imgDelete: ImageView = itemView.findViewById(R.id.btnRuleDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RuleFcAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_rule_fc, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RuleFcAdapter.ListViewHolder, position: Int) {
        val data = listRuleFc[position]
        holder.tvIdKeputusan.text = data.idGejala
        holder.tvYa.text = data.ya
        holder.tvTidak.text = data.tidak
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.imgDelete.setOnClickListener{
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listRuleFc.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(selectedRuleFc: RuleFc?, position: Int?)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedRuleFc: RuleFc?, position: Int?)
    }
}