package com.example.crudkoi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class IkanKoiAdapter(val mCtx : Context, val layoutResId : Int, val ikoiList :List<Ikankoi> ) : ArrayAdapter<Ikankoi>(mCtx, layoutResId, ikoiList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)

        val view : View = layoutInflater.inflate(layoutResId, null)

        val tvNamaIkan = view.findViewById<TextView>(R.id.tv_koi)
        val tvJumlah = view.findViewById<TextView>(R.id.tv_jumlah)

        val ikan = ikoiList[position]

        tvNamaIkan.text = ikan.nama
        tvJumlah.text = ikan.jumlah.toString()

        return view

    }
}