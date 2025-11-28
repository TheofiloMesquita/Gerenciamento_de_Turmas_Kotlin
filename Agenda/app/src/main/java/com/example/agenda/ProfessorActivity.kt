package com.example.agenda

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.agenda.adapter.ProfessorAdapter
import com.example.agenda.model.Professor
import com.example.agenda.repository.ProfessorRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfessorActivity : BaseActivity() {

    private lateinit var adapter: ProfessorAdapter
    private lateinit var addItemLayout: LinearLayout
    private lateinit var saveAllButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor)
        setToolbar("Professores", R.id.nav_professor)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        addItemLayout = findViewById(R.id.layout_add_item)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val saveItemButton = findViewById<Button>(R.id.button_salvar_item)
        saveAllButton = findViewById(R.id.button_save_all)
        val nomeEditText = findViewById<EditText>(R.id.edit_text_nome)

        setupRecyclerView(recyclerView)

        fab.setOnClickListener { view ->
            showFabMenu(view)
        }

        saveItemButton.setOnClickListener {
            val nome = nomeEditText.text.toString()
            if (nome.isNotBlank()) {
                ProfessorRepository.addProfessor(nome)
                adapter.updateData(ProfessorRepository.getProfessores())
                nomeEditText.text.clear()
                addItemLayout.visibility = View.GONE
                setUnsavedChanges(true)
            } else {
                Toast.makeText(this, "O nome não pode estar vazio", Toast.LENGTH_SHORT).show()
            }
        }

        saveAllButton.setOnClickListener {
            ProfessorRepository.saveChanges()
            setUnsavedChanges(false)
            Toast.makeText(this, "Alterações salvas com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUnsavedChanges(hasChanges: Boolean) {
        hasUnsavedChanges = hasChanges
        saveAllButton.visibility = if (hasChanges) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = ProfessorAdapter(
            ProfessorRepository.getProfessores(),
            onUpdateClick = { professor -> showUpdateDialog(professor) },
            onDeleteClick = { professor -> showDeleteDialog(professor) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showFabMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.fab_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_cadastrar -> {
                    addItemLayout.visibility = View.VISIBLE
                    setUnsavedChanges(true)
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    private fun showUpdateDialog(professor: Professor) {
        val editText = EditText(this)
        editText.setText(professor.nome)

        AlertDialog.Builder(this)
            .setTitle("Atualizar Professor")
            .setView(editText)
            .setPositiveButton("Salvar") { _, _ ->
                val novoNome = editText.text.toString()
                if (novoNome.isNotBlank()) {
                    ProfessorRepository.updateProfessor(professor.id, novoNome)
                    adapter.updateData(ProfessorRepository.getProfessores())
                    setUnsavedChanges(true)
                } else {
                    Toast.makeText(this, "O nome não pode estar vazio", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showDeleteDialog(professor: Professor) {
        AlertDialog.Builder(this)
            .setTitle("Excluir Professor")
            .setMessage("Tem certeza de que deseja excluir '${professor.nome}'?")
            .setPositiveButton("Sim") { _, _ ->
                ProfessorRepository.deleteProfessor(professor.id)
                adapter.updateData(ProfessorRepository.getProfessores())
                setUnsavedChanges(true)
            }
            .setNegativeButton("Não", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            ProfessorRepository.discardChanges()
        }
    }
}