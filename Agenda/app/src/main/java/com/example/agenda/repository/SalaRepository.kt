package com.example.agenda.repository

import com.example.agenda.model.Sala

object SalaRepository {
    private val salas = mutableListOf<Sala>(
        Sala(1, "Sala 101"),
        Sala(2, "Sala 202"),
        Sala(3, "Laboratório de Informática")
    )
    private var nextId = 4L

    private var tempSalas = mutableListOf<Sala>()

    fun getSalas(): List<Sala> {
        if (tempSalas.isEmpty()) {
            tempSalas.addAll(salas.map { it.copy() })
        }
        return tempSalas
    }

    fun addSala(nome: String) {
        tempSalas.add(Sala(nextId++, nome))
    }

    fun updateSala(id: Long, novoNome: String) {
        tempSalas.find { it.id == id }?.nome = novoNome
    }

    fun deleteSala(id: Long) {
        tempSalas.removeAll { it.id == id }
    }

    fun saveChanges() {
        salas.clear()
        salas.addAll(tempSalas.map { it.copy() })
    }

    fun discardChanges() {
        tempSalas.clear()
    }
}