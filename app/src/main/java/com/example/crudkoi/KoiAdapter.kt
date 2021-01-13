package com.example.crudkoi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudkoi.Database.Koi
import kotlinx.android.synthetic.main.adapter_koi.view.*

class KoiAdapter (private val  AllKoi: ArrayList<Koi>, private val listener: OnAdapterListener) : RecyclerView.Adapter<KoiAdapter.KoiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KoiViewHolder {
        return KoiViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_koi, parent, false)
        )
    }

    override fun getItemCount() = AllKoi.size

    override fun onBindViewHolder(holder: KoiViewHolder, position: Int) {
        val koi = AllKoi[position]
        holder.view.text_merk.text = koi.jenis
        holder.view.text_merk.setOnClickListener {
            listener.onClick(koi)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(koi)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(koi)
        }
    }

    class KoiViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Koi>) {
        AllKoi.clear()
        AllKoi.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(koi: Koi)
        fun onDelete(koi: Koi)
        fun onUpdate(helm: Koi)
    }
}