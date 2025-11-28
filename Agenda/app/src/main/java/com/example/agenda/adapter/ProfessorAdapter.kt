package com.example.agenda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.R
import com.example.agenda.model.Professor

class ProfessorAdapter(
    private var professores: List<Professor>,
    private val onUpdateClick: (Professor) -> Unit,
    private val onDeleteClick: (Professor) -> Unit
) : RecyclerView.Adapter<ProfessorAdapter.ProfessorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfessorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_professor, parent, false)
        return ProfessorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfessorViewHolder, position: Int) {
        val professor = professores[position]
        holder.bind(professor, onUpdateClick, onDeleteClick)
    }

    override fun getItemCount() = professores.size

    fun updateData(newProfessores: List<Professor>) {
        professores = newProfessores
        notifyDataSetChanged()
    }

    class ProfessorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.text_view_nome)
        private val updateButton: Button = itemView.findViewById(R.id.button_update)
        private val deleteButton: Button = itemView.findViewById(R.id.button_delete)

        fun bind(professor: Professor, onUpdateClick: (Professor) -> Unit, onDeleteClick: (Professor) -> Unit) {
            nomeTextView.text = professor.nome
            updateButton.setOnClickListener { onUpdateClick(professor) }
            deleteButton.setOnClickListener { onDeleteClick(professor) }
        }
    }
}