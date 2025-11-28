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
import com.example.agenda.adapter.EscolaAdapter
import com.example.agenda.model.Escola
import com.example.agenda.repository.EscolaRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EscolaActivity : BaseActivity() {

    private lateinit var adapter: EscolaAdapter
    private lateinit var addItemLayout: LinearLayout
    private lateinit var saveAllButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escola)
        setToolbar("Escola", R.id.nav_escola)

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
                EscolaRepository.addEscola(nome)
                adapter.updateData(EscolaRepository.getEscolas())
                nomeEditText.text.clear()
                addItemLayout.visibility = View.GONE
                setUnsavedChanges(true)
            } else {
                Toast.makeText(this, "O nome não pode estar vazio", Toast.LENGTH_SHORT).show()
            }
        }

        saveAllButton.setOnClickListener {
            EscolaRepository.saveChanges()
            setUnsavedChanges(false)
            Toast.makeText(this, "Alterações salvas com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUnsavedChanges(hasChanges: Boolean) {
        hasUnsavedChanges = hasChanges
        saveAllButton.visibility = if (hasChanges) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = EscolaAdapter(
            EscolaRepository.getEscolas(),
            onUpdateClick = { escola -> showUpdateDialog(escola) },
            onDeleteClick = { escola -> showDeleteDialog(escola) }
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

    private fun showUpdateDialog(escola: Escola) {
        val editText = EditText(this)
        editText.setText(escola.nome)

        AlertDialog.Builder(this)
            .setTitle("Atualizar Escola")
            .setView(editText)
            .setPositiveButton("Salvar") { _, _ ->
                val novoNome = editText.text.toString()
                if (novoNome.isNotBlank()) {
                    EscolaRepository.updateEscola(escola.id, novoNome)
                    adapter.updateData(EscolaRepository.getEscolas())
                    setUnsavedChanges(true)
                } else {
                    Toast.makeText(this, "O nome não pode estar vazio", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showDeleteDialog(escola: Escola) {
        AlertDialog.Builder(this)
            .setTitle("Excluir Escola")
            .setMessage("Tem certeza de que deseja excluir '${escola.nome}'?")
            .setPositiveButton("Sim") { _, _ ->
                EscolaRepository.deleteEscola(escola.id)
                adapter.updateData(EscolaRepository.getEscolas())
                setUnsavedChanges(true)
            }
            .setNegativeButton("Não", null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            EscolaRepository.discardChanges()
        }
    }
}