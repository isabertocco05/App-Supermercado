package com.example.avparcial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterListas (

    private val descListas: List<Lista>,
    private val onClick: (Lista) -> Unit
) : RecyclerView.Adapter<AdapterListas.MyViewHolder>() {

    inner class ViewHolder (
        private val binding: ListaViewBi
    )
}
