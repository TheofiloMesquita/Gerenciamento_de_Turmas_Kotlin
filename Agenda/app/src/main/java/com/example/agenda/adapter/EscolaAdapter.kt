package com.example.agenda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.R
import com.example.agenda.model.Escola

class EscolaAdapter(
    private var escolas: List<Escola>,
    private val onUpdateClick: (Escola) -> Unit,
    private val onDeleteClick: (Escola) -> Unit
) : RecyclerView.Adapter<EscolaAdapter.EscolaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscolaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_escola, parent, false)
        return EscolaViewHolder(view)
    }

    override fun onBindViewHolder(holder: EscolaViewHolder, position: Int) {
        val escola = escolas[position]
        holder.bind(escola, onUpdateClick, onDeleteClick)
    }

    override fun getItemCount() = escolas.size

    fun updateData(newEscolas: List<Escola>) {
        escolas = newEscolas
        notifyDataSetChanged()
    }

    class EscolaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.text_view_nome)
        private val updateButton: Button = itemView.findViewById(R.id.button_update)
        private val deleteButton: Button = itemView.findViewById(R.id.button_delete)

        fun bind(escola: Escola, onUpdateClick: (Escola) -> Unit, onDeleteClick: (Escola) -> Unit) {
            nomeTextView.text = escola.nome
            updateButton.setOnClickListener { onUpdateClick(escola) }
            deleteButton.setOnClickListener { onDeleteClick(escola) }
        }
    }
}