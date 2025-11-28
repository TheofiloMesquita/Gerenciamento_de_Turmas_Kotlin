package com.example.agenda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.R
import com.example.agenda.model.Aluno

class AlunoAdapter(
    private var alunos: List<Aluno>,
    private val onUpdateClick: (Aluno) -> Unit,
    private val onDeleteClick: (Aluno) -> Unit
) : RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aluno, parent, false)
        return AlunoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        val aluno = alunos[position]
        holder.bind(aluno, onUpdateClick, onDeleteClick)
    }

    override fun getItemCount() = alunos.size

    fun updateData(newAlunos: List<Aluno>) {
        alunos = newAlunos
        notifyDataSetChanged()
    }

    class AlunoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.text_view_nome)
        private val updateButton: Button = itemView.findViewById(R.id.button_update)
        private val deleteButton: Button = itemView.findViewById(R.id.button_delete)

        fun bind(aluno: Aluno, onUpdateClick: (Aluno) -> Unit, onDeleteClick: (Aluno) -> Unit) {
            nomeTextView.text = aluno.nome
            updateButton.setOnClickListener { onUpdateClick(aluno) }
            deleteButton.setOnClickListener { onDeleteClick(aluno) }
        }
    }
}