package com.example.avparcial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.avparcial.databinding.ActivityListasBinding

class AdapterListas (

    private val descListas: List<String>
//    private val imgListas: List<String>
) : RecyclerView.Adapter<AdapterListas.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // REFERENCIA DO LAYOUT
        val listaView = LayoutInflater.from(parent.context).inflate(R.layout.activity_listas, parent, false)
        return MyViewHolder(listaView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // RECUPERAR CADA ELEMENTO PELA POSIÇÃO
        val name = descListas[position]
//        val image = imgListas[position]

        holder.listName.text = name
//        holder.listImage.text = image
    }

    override fun getItemCount(): Int = descListas.size


    class MyViewHolder(listaView: View) :RecyclerView.ViewHolder(listaView){

        // SUBSTITUIR POR VIEWBINDING
        // private lateinit var binding: ActivityListasBinding
        // binding = ActivityListasBinding.inflate(layoutInflater)
        // setContentView(binding.root)

//        val listImage: ImageView = listaView.findViewById(R.id.imagemLista)
        val listName: TextView = listaView.findViewById(R.id.tituloLista)
    }

}