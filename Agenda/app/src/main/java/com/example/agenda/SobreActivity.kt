package com.example.agenda

import android.os.Bundle

class SobreActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sobre)
        setToolbar("Sobre", R.id.nav_sobre)
        hasUnsavedChanges = false // A tela Sobre não tem dados para salvar
    }
}