package com.example.agenda.repository

import com.example.agenda.model.Aluno

object AlunoRepository {
    private val alunos = mutableListOf<Aluno>(
        Aluno(1, "Ana Souza"),
        Aluno(2, "Bruno Lima"),
        Aluno(3, "Carla Martins")
    )
    private var nextId = 4L

    private var tempAlunos = mutableListOf<Aluno>()

    fun getAlunos(): List<Aluno> {
        if (tempAlunos.isEmpty()) {
            tempAlunos.addAll(alunos.map { it.copy() })
        }
        return tempAlunos
    }

    fun addAluno(nome: String) {
        tempAlunos.add(Aluno(nextId++, nome))
    }

    fun updateAluno(id: Long, novoNome: String) {
        tempAlunos.find { it.id == id }?.nome = novoNome
    }

    fun deleteAluno(id: Long) {
        tempAlunos.removeAll { it.id == id }
    }

    fun saveChanges() {
        alunos.clear()
        alunos.addAll(tempAlunos.map { it.copy() })
    }

    fun discardChanges() {
        tempAlunos.clear()
    }
}