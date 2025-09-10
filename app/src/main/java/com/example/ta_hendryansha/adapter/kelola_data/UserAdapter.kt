package com.example.ta_hendryansha.adapter.kelola_data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.entity.Admin
import com.example.ta_hendryansha.entity.Gejala
import com.example.ta_hendryansha.entity.RuleFc
import com.example.ta_hendryansha.entity.User

class UserAdapter(private val onItemClickCallback: OnItemClickCallback): RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    var listUser = ArrayList<User>()
        set(listUser){
            if(listUser.size > 0){
                this.listUser.clear()
            }
            this.listUser.addAll(listUser)
        }

    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun addItem(user: User) {
        this.listUser.add(user)
        notifyItemInserted(this.listUser.size - 1)
    }

    fun updateItem(position: Int, user: User) {
        this.listUser[position] = user
        notifyItemChanged(position, user)
    }

    fun removeItem(position: Int) {
        this.listUser.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listUser.size)
    }

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.tvNama)
        val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvJenisKelamin)
        val tvUmur: TextView = itemView.findViewById(R.id.tvUmur)
        val tvNoBpjs: TextView = itemView.findViewById(R.id.tvNoBpjs)
        val imgDelete: ImageView = itemView.findViewById(R.id.imageDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ListViewHolder, position: Int) {
        val data = listUser[position]
        holder.nama.text = data.namaLengkap
        holder.tvJenisKelamin.text = data.jenisKelamin
        holder.tvUmur.text = data.umur.toString()
        holder.tvNoBpjs.text = data.noBpjs.toString()
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(data, position)
        }
        holder.imgDelete.setOnClickListener{
            onDeleteClickCallback.onDeleteClicked(data, position)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(selectedUser: User?, position: Int?)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(selectedUser: User?, position: Int?)
    }
}