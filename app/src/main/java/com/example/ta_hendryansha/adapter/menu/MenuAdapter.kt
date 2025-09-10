package com.example.ta_hendryansha.adapter.menu

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ta_hendryansha.R
import com.example.ta_hendryansha.model.MenuModel
import com.example.ta_hendryansha.view.admin.hasilKonsultasi.ListHasilKonsultasiActivity
import com.example.ta_hendryansha.view.admin.kelola_data.ListGejalaActivity
import com.example.ta_hendryansha.view.admin.kelola_data.ListPenyakitActivity
import com.example.ta_hendryansha.view.admin.kelola_data.ListRuleCfActivity
import com.example.ta_hendryansha.view.admin.kelola_data.ListRuleFcActivity
import com.example.ta_hendryansha.view.admin.kelola_user.ListAdminActivity
import com.example.ta_hendryansha.view.admin.kelola_user.ListUserActivity
import com.example.ta_hendryansha.view.user.biodataUser.BiodataActivity
import com.example.ta_hendryansha.view.user.konsultasi.DaftarTungguKonsultasiActivity
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity
import com.example.ta_hendryansha.view.user.konsultasi.KonsultasiActivity.Companion.EXTRA_KODE
import com.example.ta_hendryansha.view.main.MainActivity
import com.example.ta_hendryansha.view.user.riwayatKonsultasi.RiwayatActivity

class MenuAdapter(private val listMenu: ArrayList<MenuModel>): RecyclerView.Adapter<MenuAdapter.ListViewHolder>(){

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.imageMenu)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }

    fun updateData(newList: List<MenuModel>) {
        listMenu.clear()
        listMenu.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_menu, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listMenu[position]
        holder.tvTitle.text = data.tvTitle
        holder.image.setImageResource(data.imageMenu)

        holder.itemView.setOnClickListener{

            if(data.tvTitle == "Diagnosis"){
                val intent = Intent(holder.itemView.context, KonsultasiActivity::class.java)
                intent.putExtra(EXTRA_KODE, "G010")
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Konsultasi Dokter"){
                val intent = Intent(holder.itemView.context, DaftarTungguKonsultasiActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Biodata"){
                val intent = Intent(holder.itemView.context, BiodataActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Data Gejala"){
                val intent = Intent(holder.itemView.context, ListGejalaActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Data Penyakit"){
                val intent = Intent(holder.itemView.context, ListPenyakitActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data. tvTitle == "Data Rule Cf"){
                val intent = Intent(holder.itemView.context, ListRuleCfActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Data Rule Fc"){
                val intent = Intent(holder.itemView.context, ListRuleFcActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Data Akun User"){
                val intent = Intent(holder.itemView.context, ListUserActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Data Akun Admin"){
                val intent = Intent(holder.itemView.context, ListAdminActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Riwayat"){
                val intent = Intent(holder.itemView.context, ListHasilKonsultasiActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }else if(data.tvTitle == "Riwayat Penyakit"){
                val intent = Intent(holder.itemView.context, RiwayatActivity::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

}