package com.example.avparcial

import Lista
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.avparcial.databinding.ListaViewBinding

class AdapterListas (
    private val descListas: List<Lista>,
    private val onClick: (Lista) -> Unit
) : RecyclerView.Adapter<AdapterListas.ViewHolder>() {

    inner class ViewHolder (
        private val binding: ListaViewBinding
    ) : RecyclerView.ViewHolder(binding.root){
        private var currentItem: Lista? = null

        init{
            itemView.setOnClickListener{
                currentItem?.let {
                    onClick(it)
                    val intent = Intent(binding.root.context, ItensLista::class.java)
                    binding.root.context.startActivity(intent)
                }
            }
        }

        fun bind (item: Lista){
            currentItem = item

            binding.nameList.text = item.nome
            Glide.with(binding.root.context)
                .load(item.imagemUrl)
                .centerCrop()
                .placeholder(R.drawable.loader_test)
                .into(binding.imageList)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListaViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = descListas[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int =descListas.size
}
