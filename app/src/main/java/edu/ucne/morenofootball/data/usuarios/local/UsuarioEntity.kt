package edu.ucne.morenofootball.data.usuarios.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val usuarioId: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val registerDate: String,
    val rememberUser: Boolean
)