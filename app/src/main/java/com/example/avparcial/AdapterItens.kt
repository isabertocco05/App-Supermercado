package com.example.avparcial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.avparcial.databinding.ActivityItensListaBinding
import com.example.avparcial.databinding.ItensViewBinding

class AdapterItens(
    private val descItens: List<Itens>,
    private val onClick: (Itens) -> Unit
): RecyclerView.Adapter<AdapterItens.ViewHolder>()  {

    inner class ViewHolder(
        private val binding: ItensViewBinding
    ): RecyclerView.ViewHolder(binding.root){
        private var currentItem: Itens? = null

        init{
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Itens){
            currentItem = item

            binding.nameItem.text = item.nome_item
            binding.quantidade.inputType = item.quantidade
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItensViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = descItens[position]
        holder.bind((item))
    }

    override fun getItemCount(): Int = descItens.size

}