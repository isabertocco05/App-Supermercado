package com.example.avparcial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class CategoriaAdapter(context: Context, private val categorias: List<Categoria>) :
    ArrayAdapter<Categoria>(context, 0, categorias) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.categorias_dropdown, parent, false)
        val item = categorias[position]

        val itemIcon = view.findViewById<ImageView>(R.id.item_icon)
        val itemText = view.findViewById<TextView>(R.id.item_text)

        itemIcon.setImageResource(item.icone)
        itemText.text = item.elemento

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}
