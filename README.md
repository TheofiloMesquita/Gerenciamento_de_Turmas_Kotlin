📚 Sistema de Gestão Escolar
🏫 Sobre o Projeto

Com o objetivo de oferecer uma solução moderna e integrada para a administração de uma rede de escolas, este aplicativo foi desenvolvido para centralizar e simplificar todos os processos relacionados à gestão acadêmica e operacional.

A plataforma possibilita o gerenciamento completo de alunos, escolas, professores e salas, reunindo em um único sistema todas as informações essenciais para o funcionamento eficiente das instituições.

🎯 Principais Funcionalidades

👨‍🎓 Gestão de Alunos
Cadastro, acompanhamento, matrículas e histórico educacional.

🏫 Gestão de Escolas
Configuração de unidades, calendários, horários e dados institucionais.

👨‍🏫 Gestão de Professores
Perfis, alocação em turmas, distribuição de aulas e organização pedagógica.

🧩 Gestão de Salas
Controle de disponibilidade, capacidade, reservas e distribuição de turmas.

🚀 Objetivo

Proporcionar uma experiência completa de administração escolar, oferecendo ferramentas que trazem:

Agilidade nos processos

Redução de erros manuais

Integração entre setores

Tomada de decisão mais precisa

Visão global do funcionamento da rede educacional

--------------------------------------------------------------------------------------------------------------------------
CRUD
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

É um repositório responsável por gerenciar os dados das escolas dentro do sistema. Ele mantém uma lista de escolas e permite realizar operações básicas de maneira controlada.

O funcionamento se baseia em duas listas:

Lista principal → guarda os dados oficiais.

Lista temporária → recebe as alterações antes de serem confirmadas.

Isso permite fazer mudanças sem afetar diretamente os dados originais.

O repositório permite:

Listar escolas

Adicionar novas escolas

Alterar informações existentes

Remover escolas

Confirmar mudanças feitas

Descartar alterações não salvas


--------------------------------------------------------------------------------------------------------------------------
 Telas

<img width="324" height="688" alt="image" src="https://github.com/user-attachments/assets/3fd90230-08b8-4da7-96d9-56d5134c377a" />

<img width="325" height="689" alt="image" src="https://github.com/user-attachments/assets/1321a2ed-e110-4fab-9df3-0d6c4812e08a" />

<img width="319" height="690" alt="image" src="https://github.com/user-attachments/assets/080e5547-a064-4bb9-a9f1-e9a1e96c880b" />

<img width="335" height="737" alt="image" src="https://github.com/user-attachments/assets/6bd62023-6461-40f3-ac6f-aea7e122c046" />

<img width="319" height="693" alt="image" src="https://github.com/user-attachments/assets/e60af8ea-3194-4c54-bd13-1f0aada04959" />

<img width="327" height="699" alt="image" src="https://github.com/user-attachments/assets/62754d29-b265-4b50-8f62-7af52a61875b" />








