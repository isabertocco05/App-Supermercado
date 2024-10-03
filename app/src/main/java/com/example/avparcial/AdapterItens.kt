package com.example.avparcial


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.avparcial.databinding.ItensViewBinding

class AdapterItens(
    private var descItens: MutableList<Itens> = mutableListOf(),
    private val onClick: (Itens) -> Unit,
    private val onDeleteClicked: (Itens) -> Unit
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

            binding.itemCheck.setOnClickListener{
                currentItem?.let { item ->
                    if (binding.itemCheck.isChecked) {
                        item.checked = true
                        notifyDataSetChanged()
                    }
                    else{
                        item.checked = false
                        notifyDataSetChanged()
                    }
                }
            }

            binding.deleteItem.setOnClickListener {
                currentItem?.let { item ->
                    onDeleteClicked(item)
                }
            }
        }

        fun bind(item: Itens){
            currentItem = item
            binding.nameItem.text = item.nome_item.uppercase()
            binding.quantidade.text= item.quantidade
            binding.un.text = item.unidade

            if (item.checked) {
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_surfaceContainerHighest_mediumContrast))
            }
            else{
                binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.md_theme_background))
            }


            val iconeCategoria = when (item.categoria) {
                "Fruta" -> R.mipmap.ic_fruta
                "Legumes e Vegetais" -> R.mipmap.ic_legumes
                "Comida" -> R.mipmap.ic_comida
                "Carne" -> R.mipmap.ic_carne
                "Bebida" -> R.mipmap.ic_bebida
                else -> R.mipmap.ic_comida
            }
            binding.image.setImageResource(iconeCategoria)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItensViewBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = descItens[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = descItens.size


}