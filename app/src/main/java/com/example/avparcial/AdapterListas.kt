package com.example.avparcial

import Lista
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.avparcial.databinding.ListaViewBinding

class AdapterListas (
    private val descListas: MutableList<Lista>,
    private val onClick: (Lista) -> Unit
) : RecyclerView.Adapter<AdapterListas.ViewHolder>() {

    private val listasSelecionadas = mutableSetOf<Lista>()
    private var isDeleteMode = false

    inner class ViewHolder (
        private val binding: ListaViewBinding
    ) : RecyclerView.ViewHolder(binding.root){
        private var currentItem: Lista? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    if (isDeleteMode) {
                        toggleSelection(it)
                    } else {
                        onClick(it)
                        val intent = Intent(binding.root.context, ItensLista::class.java)
                        intent.putExtra("lista", it)
                        binding.root.context.startActivity(intent)
                    }
                }
            }

            // para deletar listas
            itemView.setOnLongClickListener {
                if (!isDeleteMode) {
                    isDeleteMode = true
                    currentItem?.let { toggleSelection(it) }
                    notifyDataSetChanged()
                }
                true
            }
        }

        fun bind(item: Lista) {
            currentItem = item
            binding.nameList.text = item.nome.uppercase()

            Glide.with(binding.root.context)
                .load(item.imagemUrl)
                .override(170,170)
                .centerCrop()
                .placeholder(R.mipmap.placeholder_food)
                .into(binding.imageList)
            itemView.isSelected = listasSelecionadas.contains(item)

            if (isDeleteMode) {
                binding.checkBox.visibility = View.VISIBLE
                binding.checkBox.isChecked = listasSelecionadas.contains(item)
            } else {
                binding.checkBox.visibility = View.GONE
            }
        }


        private fun toggleSelection(item: Lista) {
            if (listasSelecionadas.contains(item)) {
                listasSelecionadas.remove(item)
            } else {
                listasSelecionadas.add(item)
            }
            notifyItemChanged(descListas.indexOf(item))
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

    fun deleteListasSelecionadas() {
        descListas.removeAll(listasSelecionadas)
        listasSelecionadas.clear()
        isDeleteMode = false
        notifyDataSetChanged()
    }

    fun startDeleteMode() {
        isDeleteMode = true
        listasSelecionadas.clear()
        notifyDataSetChanged()
    }

    fun cancelDeleteMode() {
        isDeleteMode = false
        listasSelecionadas.clear()
        notifyDataSetChanged()
    }


    fun checkDeleteMode() {
        if (!isDeleteMode) {
            startDeleteMode()
        } else {
            if (listasSelecionadas.isNotEmpty()) {
                deleteListasSelecionadas()
            } else {
                cancelDeleteMode()
            }
        }
    }

}
