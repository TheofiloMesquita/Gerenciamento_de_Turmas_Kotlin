package com.example.agenda.repository

import com.example.agenda.model.Professor

object ProfessorRepository {
    private val professores = mutableListOf<Professor>(
        Professor(1, "João da Silva"),
        Professor(2, "Maria Oliveira"),
        Professor(3, "Carlos Pereira")
    )
    private var nextId = 4L

    private var tempProfessores = mutableListOf<Professor>()

    fun getProfessores(): List<Professor> {
        if (tempProfessores.isEmpty()) {
            tempProfessores.addAll(professores.map { it.copy() })
        }
        return tempProfessores
    }

    fun addProfessor(nome: String) {
        tempProfessores.add(Professor(nextId++, nome))
    }

    fun updateProfessor(id: Long, novoNome: String) {
        tempProfessores.find { it.id == id }?.nome = novoNome
    }

    fun deleteProfessor(id: Long) {
        tempProfessores.removeAll { it.id == id }
    }

    fun saveChanges() {
        professores.clear()
        professores.addAll(tempProfessores.map { it.copy() })
    }

    fun discardChanges() {
        tempProfessores.clear()
    }
}