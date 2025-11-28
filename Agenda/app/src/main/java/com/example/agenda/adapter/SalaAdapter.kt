package com.example.agenda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.R
import com.example.agenda.model.Sala

class SalaAdapter(
    private var salas: List<Sala>,
    private val onUpdateClick: (Sala) -> Unit,
    private val onDeleteClick: (Sala) -> Unit
) : RecyclerView.Adapter<SalaAdapter.SalaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sala, parent, false)
        return SalaViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalaViewHolder, position: Int) {
        val sala = salas[position]
        holder.bind(sala, onUpdateClick, onDeleteClick)
    }

    override fun getItemCount() = salas.size

    fun updateData(newSalas: List<Sala>) {
        salas = newSalas
        notifyDataSetChanged()
    }

    class SalaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.text_view_nome)
        private val updateButton: Button = itemView.findViewById(R.id.button_update)
        private val deleteButton: Button = itemView.findViewById(R.id.button_delete)

        fun bind(sala: Sala, onUpdateClick: (Sala) -> Unit, onDeleteClick: (Sala) -> Unit) {
            nomeTextView.text = sala.nome
            updateButton.setOnClickListener { onUpdateClick(sala) }
            deleteButton.setOnClickListener { onDeleteClick(sala) }
        }
    }
}