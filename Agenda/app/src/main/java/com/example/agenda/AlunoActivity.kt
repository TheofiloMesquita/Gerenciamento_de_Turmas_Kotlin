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
import com.example.agenda.adapter.AlunoAdapter
import com.example.agenda.model.Aluno
import com.example.agenda.repository.AlunoRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AlunoActivity : BaseActivity() {

    private lateinit var adapter: AlunoAdapter
    private lateinit var addItemLayout: LinearLayout
    private lateinit var saveAllButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aluno)
        setToolbar("Alunos", R.id.nav_aluno)

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
                AlunoRepository.addAluno(nome)
                adapter.updateData(AlunoRepository.getAlunos())
                nomeEditText.text.clear()
                addItemLayout.visibility = View.GONE
                setUnsavedChanges(true)
            } else {
                Toast.makeText(this, "O nome não pode estar vazio", Toast.LENGTH_SHORT).show()
            }
        }

        saveAllButton.setOnClickListener {
            AlunoRepository.saveChanges()
            setUnsavedChanges(false)
            Toast.makeText(this, "Alterações salvas com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUnsavedChanges(hasChanges: Boolean) {
        hasUnsavedChanges = hasChanges
        saveAllButton.visibility = if (hasChanges) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = AlunoAdapter(
            AlunoRepository.getAlunos(),
            onUpdateClick = { aluno -> showUpdateDialog(aluno) },
            onDeleteClick = { aluno -> showDeleteDialog(aluno) }
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

    private fun showUpdateDialog(aluno: Aluno) {
        val editText = EditText(this)
        editText.setText(aluno.nome)

        AlertDialog.Builder(this)
            .setTitle("Atualizar Aluno")
            .setView(editText)
            .setPositiveButton("Salvar") { _, _ ->
                val novoNome = editText.text.toString()
                if (novoNome.isNotBlank()) {
                    AlunoRepository.updateAluno(aluno.id, novoNome)
                    adapter.updateData(AlunoRepository.getAlunos())
                    setUnsavedChanges(true)
                } else {
                    Toast.makeText(this, "O nome não pode estar vazio", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showDeleteDialog(aluno: Aluno) {
        AlertDialog.Builder(this)
            .setTitle("Excluir Aluno")
            .setMessage("Tem certeza de que deseja excluir '${aluno.nome}'?")
            .setPositiveButton("Sim") { _, _ ->
                AlunoRepository.deleteAluno(aluno.id)
                adapter.updateData(AlunoRepository.getAlunos())
                setUnsavedChanges(true)
            }
            .setNegativeButton("Não", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            AlunoRepository.discardChanges()
        }
    }
}