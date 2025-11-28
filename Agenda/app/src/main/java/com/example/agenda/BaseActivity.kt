package com.example.agenda

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private var currentNavMenuItemId: Int = 0
    protected var hasUnsavedChanges = false

    override fun setContentView(layoutResID: Int) {
        val fullView = layoutInflater.inflate(R.layout.activity_base, null) as DrawerLayout
        val activityContainer = fullView.findViewById<FrameLayout>(R.id.content_frame)
        layoutInflater.inflate(layoutResID, activityContainer, true)
        super.setContentView(fullView)

        drawerLayout = fullView
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    protected fun setToolbar(title: String, navMenuItemId: Int) {
        supportActionBar?.title = title
        navigationView.setCheckedItem(navMenuItemId)
        currentNavMenuItemId = navMenuItemId
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)

        if (item.itemId == currentNavMenuItemId) {
            return true // Não faz nada se o item já estiver selecionado
        }

        val intent = when (item.itemId) {
            R.id.nav_sala -> Intent(this, SalaActivity::class.java)
            R.id.nav_professor -> Intent(this, ProfessorActivity::class.java)
            R.id.nav_aluno -> Intent(this, AlunoActivity::class.java)
            R.id.nav_escola -> Intent(this, EscolaActivity::class.java)
            R.id.nav_home -> Intent(this, HomeActivity::class.java)
            R.id.nav_sobre -> Intent(this, SobreActivity::class.java)
            else -> null
        }

        if (intent != null) {
            if (hasUnsavedChanges) {
                showConfirmationDialog(intent, item)
            } else {
                startActivity(intent)
                finish()
            }
        }

        return true
    }

    private fun showConfirmationDialog(intent: Intent, item: MenuItem) {
        AlertDialog.Builder(this)
            .setTitle("Atenção")
            .setMessage("Ao sair da página sem salvar, poderá perder todo o progresso. Tem certeza que deseja sair?")
            .setPositiveButton("Sim") { _, _ ->
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Não") { _, _ ->
                // Reverte a seleção do menu para o item atual
                navigationView.setCheckedItem(currentNavMenuItemId)
            }
            .show()
    }
}