package com.example.agenda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        findViewById<Button>(R.id.btn_sala).setOnClickListener {
            startActivity(Intent(this, SalaActivity::class.java))
        }

        findViewById<Button>(R.id.btn_professor).setOnClickListener {
            startActivity(Intent(this, ProfessorActivity::class.java))
        }

        findViewById<Button>(R.id.btn_aluno).setOnClickListener {
            startActivity(Intent(this, AlunoActivity::class.java))
        }

        findViewById<Button>(R.id.btn_escola).setOnClickListener {
            startActivity(Intent(this, EscolaActivity::class.java))
        }
    }
}