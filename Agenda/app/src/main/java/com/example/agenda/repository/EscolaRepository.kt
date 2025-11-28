package com.example.agenda.repository

import com.example.agenda.model.Escola

object EscolaRepository {
    private val escolas = mutableListOf<Escola>(
        Escola(1, "Escola Municipal Padrão"),
        Escola(2, "Colégio Estadual Central"),
        Escola(3, "Instituto de Educação Superior")
    )
    private var nextId = 4L

    private var tempEscolas = mutableListOf<Escola>()

    fun getEscolas(): List<Escola> {
        if (tempEscolas.isEmpty()) {
            tempEscolas.addAll(escolas.map { it.copy() })
        }
        return tempEscolas
    }

    fun addEscola(nome: String) {
        tempEscolas.add(Escola(nextId++, nome))
    }

    fun updateEscola(id: Long, novoNome: String) {
        tempEscolas.find { it.id == id }?.nome = novoNome
    }

    fun deleteEscola(id: Long) {
        tempEscolas.removeAll { it.id == id }
    }

    fun saveChanges() {
        escolas.clear()
        escolas.addAll(tempEscolas.map { it.copy() })
    }

    fun discardChanges() {
        tempEscolas.clear()
    }
}